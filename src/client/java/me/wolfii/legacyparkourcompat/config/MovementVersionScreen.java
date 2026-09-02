package me.wolfii.legacyparkourcompat.config;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.config.version.MovementVersions;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class MovementVersionScreen extends OptionsSubScreen {
    private static final int CONTROL_WIDTH = 310;
    private static final int SCREEN_MARGIN = 20;
    private static final int HEADER_MARGIN = 16;
    private static final int ITEM_HEIGHT = 20;
    private static final Component TITLE = Component.translatable("legacyparkourcompat.version.title");
    private static final Component CURRENT_LABEL = Component.translatable("legacyparkourcompat.version.current");
    private static final Component NONE_AVAILABLE = Component.translatable("legacyparkourcompat.version.available.none");

    private final boolean serverForced = MovementVersions.isServerForced();
    private ParkourVersion selected = MovementVersions.selectedForUi();
    private boolean committed;
    private LinearLayout header;
    private MultiLineTextWidget statusWidget;
    private VersionList versionList;

    public MovementVersionScreen(Screen lastScreen) {
        super(lastScreen, Minecraft.getInstance().options, TITLE);
    }

    private static Component label(ParkourVersion version) {
        return version.isCurrent() ? CURRENT_LABEL : Component.literal(version.displayLabel());
    }

    private int controlWidth() {
        return Math.max(20, Math.min(CONTROL_WIDTH, this.width - SCREEN_MARGIN * 2));
    }

    @Override
    protected void addTitle() {
        this.header = this.layout.addToHeader(LinearLayout.vertical().spacing(6));
        this.header.defaultCellSetting().alignHorizontallyCenter();
        this.header.addChild(new StringWidget(this.title, this.font));
        this.statusWidget = new MultiLineTextWidget(Component.empty(), this.font)
            .setMaxWidth(CONTROL_WIDTH)
            .setCentered(true);
        this.header.addChild(this.statusWidget);
    }

    @Override
    protected void addOptions() {
    }

    @Override
    protected void addContents() {
        List<ParkourVersion> versions = new ArrayList<>();
        versions.add(ParkourVersion.CURRENT);
        versions.addAll(MovementVersions.listedVersions());
        if (versions.size() == 1) {
            this.layout.addToContents(new MultiLineTextWidget(NONE_AVAILABLE, this.font)
                .setMaxWidth(CONTROL_WIDTH)
                .setCentered(true));
        } else {
            this.versionList = this.layout.addToContents(new VersionList(this, versions));
        }
        this.refreshStatus();
    }

    @Override
    protected void repositionElements() {
        int controlWidth = this.controlWidth();
        if (this.statusWidget != null) {
            this.statusWidget.setMaxWidth(controlWidth);
        }
        if (this.header != null) {
            this.header.arrangeElements();
            int maxHeader = Math.max(
                HeaderAndFooterLayout.DEFAULT_HEADER_AND_FOOTER_HEIGHT,
                this.height - this.layout.getFooterHeight() - 40
            );
            this.layout.setHeaderHeight(Math.min(this.header.getHeight() + HEADER_MARGIN, maxHeader));
        }
        super.repositionElements();
        if (this.versionList != null) {
            int listWidth = this.controlWidth();
            int x = (this.width - listWidth) / 2;
            this.versionList.updateSizeAndPosition(
                listWidth,
                this.layout.getContentHeight(),
                x,
                this.layout.getHeaderHeight()
            );
        }
    }

    @Override
    public void onClose() {
        this.commit();
        super.onClose();
    }

    @Override
    public void removed() {
        this.commit();
        super.removed();
    }

    private void commit() {
        if (this.committed || this.serverForced) {
            return;
        }
        this.committed = true;
        MovementVersions.select(this.selected);
    }

    private void selectListedVersion(ParkourVersion version) {
        if (this.serverForced) {
            return;
        }
        this.selected = version;
        this.refreshStatus();
    }

    private void refreshStatus() {
        if (this.statusWidget == null) {
            return;
        }
        if (this.serverForced) {
            Component forced = Component.translatable(
                    "legacyparkourcompat.version.status.forced",
                    label(this.selected).getString())
                .withStyle(ChatFormatting.GOLD);
            if (this.selected.isPartiallyImplemented()) {
                forced = forced.copy()
                    .append("\n")
                    .append(Component.translatable("legacyparkourcompat.version.status.partial")
                        .withStyle(ChatFormatting.YELLOW));
            }
            this.statusWidget.setMessage(forced);
            return;
        }
        if (this.selected.isCurrent()) {
            this.statusWidget.setMessage(Component.translatable("legacyparkourcompat.version.status.vanilla")
                .withStyle(ChatFormatting.GRAY));
            return;
        }
        this.statusWidget.setMessage(this.selected.isPartiallyImplemented()
            ? Component.translatable("legacyparkourcompat.version.status.partial").withStyle(ChatFormatting.YELLOW)
            : Component.empty());
    }

    private static final class VersionList extends ObjectSelectionList<VersionList.Entry> {
        private final MovementVersionScreen screen;

        VersionList(MovementVersionScreen screen, List<ParkourVersion> versions) {
            super(screen.minecraft, 0, 0, 0, ITEM_HEIGHT);
            this.screen = screen;
            Entry selected = null;
            for (ParkourVersion version : versions) {
                Entry entry = new Entry(this, version);
                this.addEntry(entry);
                if (version == screen.selected) {
                    selected = entry;
                }
            }
            if (selected != null) {
                super.setSelected(selected);
                this.centerScrollOn(selected);
            }
        }

        @Override
        public void setSelected(Entry entry) {
            if (this.screen.serverForced) {
                return;
            }
            super.setSelected(entry);
            if (entry != null) {
                this.screen.selectListedVersion(entry.version);
            }
        }

        @Override
        protected boolean entriesCanBeSelected() {
            return !this.screen.serverForced;
        }

        @Override
        public int getRowWidth() {
            return this.getWidth();
        }

        private static final class Entry extends ObjectSelectionList.Entry<Entry> {
            private final VersionList list;
            private final ParkourVersion version;
            private final Component label;

            Entry(VersionList list, ParkourVersion version) {
                this.list = list;
                this.version = version;
                this.label = MovementVersionScreen.label(version);
            }

            @Override
            public Component getNarration() {
                return this.label;
            }

            @Override
            public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
                if (this.list.screen.serverForced) {
                    return false;
                }
                this.list.setSelected(this);
                return true;
            }

            @Override
            public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
                int color = hovered || this.list.getSelected() == this ? 0xFFFFFFFF : 0xFFAAAAAA;
                graphics.text(
                    Minecraft.getInstance().font,
                    this.label,
                    this.getContentXMiddle() - Minecraft.getInstance().font.width(this.label) / 2,
                    this.getContentYMiddle() - 4,
                    color,
                    true
                );
            }
        }
    }
}
