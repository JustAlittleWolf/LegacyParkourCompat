package me.wolfii.legacyparkourcompat.config;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.config.version.MovementVersions;
import me.wolfii.legacyparkourcompat.config.version.VersionStatus;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;

import java.util.List;

public class MovementVersionScreen extends Screen {
    private static final int FIELD_WIDTH = 200;
    private static final int FIELD_HEIGHT = 20;
    private static final int CONTENT_WIDTH = 310;
    private static final int LIST_HEIGHT = 140;
    private static final int ITEM_HEIGHT = 20;
    private static final Component TITLE = Component.translatable("legacyparkourcompat.version.title");
    private static final Component ENABLED = Component.translatable("legacyparkourcompat.version.enabled");
    private static final Component FIELD_LABEL = Component.translatable("legacyparkourcompat.version.field");
    private static final Component HINT = Component.translatable("legacyparkourcompat.version.hint");
    private static final Component DESCRIPTION = Component.translatable("legacyparkourcompat.version.description");
    private static final Component AVAILABLE = Component.translatable("legacyparkourcompat.version.available");
    private static final Component NONE_AVAILABLE = Component.translatable("legacyparkourcompat.version.available.none");

    private final Screen lastScreen;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private boolean wanted = MovementVersions.isWanted();
    private CycleButton<Boolean> enabledButton;
    private EditBox versionBox;
    private StringWidget statusWidget;
    private MultiLineTextWidget partialNote;
    private VersionList versionList;

    public MovementVersionScreen(Screen lastScreen) {
        super(TITLE);
        this.lastScreen = lastScreen;
    }

    private static Component statusMessage(VersionStatus status, String value) {
        String normalized = MovementVersions.normalize(value);
        return switch (status) {
            case VANILLA -> Component.translatable("legacyparkourcompat.version.status.vanilla")
                .withStyle(ChatFormatting.GRAY);
            case VALID -> Component.translatable("legacyparkourcompat.version.status.implemented", normalized)
                .withStyle(ChatFormatting.WHITE);
            case INVALID -> Component.translatable("legacyparkourcompat.version.status.invalid")
                .withStyle(ChatFormatting.RED);
        };
    }

    private static int color(VersionStatus status) {
        return status == VersionStatus.INVALID
            ? TextColor.RED.getValue() | 0xFF000000
            : EditBox.DEFAULT_TEXT_COLOR;
    }

    @Override
    protected void init() {
        this.layout.removeChildren();
        this.layout.addTitleHeader(this.title, this.font);

        LinearLayout contents = this.layout.addToContents(LinearLayout.vertical().spacing(8));
        contents.defaultCellSetting().alignHorizontallyCenter();

        contents.addChild(new MultiLineTextWidget(DESCRIPTION, this.font)
            .setMaxWidth(CONTENT_WIDTH)
            .setCentered(true));

        this.enabledButton = CycleButton.onOffBuilder(this.wanted)
            .create(0, 0, CONTENT_WIDTH, 20, ENABLED, (button, value) -> this.setWanted(value));
        contents.addChild(this.enabledButton);

        contents.addChild(new StringWidget(FIELD_LABEL, this.font));
        this.versionBox = new EditBox(this.font, FIELD_WIDTH, FIELD_HEIGHT, FIELD_LABEL);
        this.versionBox.setMaxLength(64);
        this.versionBox.setHint(HINT);
        this.versionBox.setResponder(this::onVersionChanged);
        this.versionBox.setValue(MovementVersions.getInput());
        contents.addChild(this.versionBox);

        this.statusWidget = new StringWidget(Component.empty(), this.font).setMaxWidth(CONTENT_WIDTH);
        contents.addChild(this.statusWidget);
        this.partialNote = new MultiLineTextWidget(Component.empty(), this.font)
            .setMaxWidth(CONTENT_WIDTH)
            .setCentered(true);
        contents.addChild(this.partialNote);

        contents.addChild(new StringWidget(AVAILABLE, this.font));
        List<ParkourVersion> versions = MovementVersions.listedVersions();
        if (versions.isEmpty()) {
            contents.addChild(new MultiLineTextWidget(NONE_AVAILABLE, this.font)
                .setMaxWidth(CONTENT_WIDTH)
                .setCentered(true));
        } else {
            this.versionList = new VersionList(this, this.minecraft, CONTENT_WIDTH, LIST_HEIGHT, ITEM_HEIGHT, versions);
            contents.addChild(this.versionList);
        }

        this.layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> this.onClose()).width(200).build());
        this.layout.visitWidgets(this::addRenderableWidget);
        this.refreshInteractiveState();
        this.refreshStatus();
        this.repositionElements();
    }

    @Override
    protected void setInitialFocus() {
        if (this.wanted && this.versionBox != null) {
            this.setInitialFocus(this.versionBox);
        } else if (this.enabledButton != null) {
            this.setInitialFocus(this.enabledButton);
        }
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.lastScreen);
    }

    private void setWanted(boolean wanted) {
        this.wanted = wanted;
        MovementVersions.setWanted(wanted);
        this.refreshInteractiveState();
        this.refreshStatus();
    }

    private void selectListedVersion(ParkourVersion version) {
        if (!this.wanted) {
            return;
        }
        this.versionBox.setValue(version.id());
    }

    private void onVersionChanged(String value) {
        MovementVersions.setTypedValue(value);
        this.refreshStatus();
        if (this.versionList != null) {
            this.versionList.syncSelection();
        }
    }

    private void refreshInteractiveState() {
        if (this.versionBox != null) {
            this.versionBox.setEditable(this.wanted);
            this.versionBox.active = this.wanted;
        }
        if (this.versionList != null) {
            this.versionList.active = this.wanted;
        }
    }

    private void refreshStatus() {
        if (this.versionBox == null || this.statusWidget == null || this.partialNote == null) {
            return;
        }
        if (!this.wanted) {
            this.versionBox.setTextColor(EditBox.DEFAULT_TEXT_COLOR);
            this.statusWidget.setMessage(Component.translatable("legacyparkourcompat.version.status.disabled")
                .withStyle(ChatFormatting.GRAY));
            this.partialNote.setMessage(Component.empty());
            this.partialNote.visible = false;
            return;
        }
        String value = this.versionBox.getValue();
        VersionStatus status = MovementVersions.status(value);
        this.versionBox.setTextColor(color(status));
        this.statusWidget.setMessage(statusMessage(status, value));
        ParkourVersion selected = MovementVersions.parkourVersion(MovementVersions.normalize(value));
        boolean partial = status == VersionStatus.VALID && selected != null && selected.isPartiallyImplemented();
        this.partialNote.setMessage(partial
            ? Component.translatable("legacyparkourcompat.version.status.partial").withStyle(ChatFormatting.YELLOW)
            : Component.empty());
        this.partialNote.visible = partial;
    }

    private static class VersionList extends ObjectSelectionList<VersionList.Entry> {
        private final MovementVersionScreen screen;

        VersionList(
            MovementVersionScreen screen,
            Minecraft minecraft,
            int width,
            int height,
            int itemHeight,
            List<ParkourVersion> versions
        ) {
            super(minecraft, width, height, 0, itemHeight);
            this.screen = screen;
            for (ParkourVersion version : versions) {
                this.addEntry(new Entry(this, version));
            }
            this.syncSelection();
        }

        void syncSelection() {
            ParkourVersion selected = MovementVersions.parkourVersion(MovementVersions.getInput());
            Entry match = null;
            if (selected != null && !selected.isCurrent()) {
                for (Entry entry : this.children()) {
                    if (entry.version == selected) {
                        match = entry;
                        break;
                    }
                }
            }
            this.setSelected(match);
        }

        @Override
        public int getRowWidth() {
            return this.getWidth() - 10;
        }

        @Override
        protected int scrollBarX() {
            return this.getRight() - 6;
        }

        private static class Entry extends ObjectSelectionList.Entry<Entry> {
            private final VersionList list;
            private final ParkourVersion version;
            private final Component label;

            Entry(VersionList list, ParkourVersion version) {
                this.list = list;
                this.version = version;
                String range = displayRange(version);
                this.label = version.isPartiallyImplemented()
                    ? Component.translatable("legacyparkourcompat.version.list.partial", range)
                    : Component.literal(range);
            }

            private static String displayRange(ParkourVersion version) {
                List<String> patches = version.patches();
                if (patches.size() <= 1) {
                    return version.id();
                }
                return patches.getFirst() + " – " + patches.getLast();
            }

            @Override
            public Component getNarration() {
                return this.label;
            }

            @Override
            public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
                this.list.screen.selectListedVersion(this.version);
                return true;
            }

            @Override
            public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
                int color = this.list.screen.wanted ? 0xFFFFFFFF : 0xFF808080;
                graphics.text(
                    Minecraft.getInstance().font,
                    this.label,
                    this.getContentX() + 4,
                    this.getContentYMiddle() - 4,
                    color,
                    true
                );
            }
        }
    }
}
