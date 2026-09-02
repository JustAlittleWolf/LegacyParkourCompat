package me.wolfii.legacyparkourcompat.config;

import me.wolfii.legacyparkourcompat.api.MovementController;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.config.version.MovementVersions;
import me.wolfii.legacyparkourcompat.config.version.VersionStatus;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractScrollArea;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class MovementVersionScreen extends OptionsSubScreen {
    private static final int CONTROL_WIDTH = 310;
    private static final int FIELD_HEIGHT = 20;
    private static final int DISABLED_TEXT_COLOR = 0xFFAA0000;
    private static final Component TITLE = Component.translatable("legacyparkourcompat.version.title");
    private static final Component ENABLED = Component.translatable("legacyparkourcompat.version.enabled");
    private static final Component FIELD_LABEL = Component.translatable("legacyparkourcompat.version.field");
    private static final Component HINT = Component.translatable("legacyparkourcompat.version.hint");
    private static final Component DISABLED_FIELD = Component.translatable("legacyparkourcompat.version.field.disabled");
    private static final Component AVAILABLE = Component.translatable("legacyparkourcompat.version.available");
    private static final Component NONE_AVAILABLE = Component.translatable("legacyparkourcompat.version.available.none");

    private final boolean serverForced = MovementVersions.isServerForced();
    private boolean wanted = MovementVersions.isWanted();
    private String draftInput = MovementVersions.getInput();
    private boolean suppressFieldResponder;
    private boolean committed;
    private LinearLayout header;
    private CycleButton<Boolean> enabledButton;
    private EditBox versionBox;
    private MultiLineTextWidget statusWidget;
    private VersionLabelsWidget versionLabels;

    public MovementVersionScreen(Screen lastScreen) {
        super(lastScreen, Minecraft.getInstance().options, TITLE);
        if (this.serverForced) {
            this.wanted = MovementController.get().isEnabled();
            this.draftInput = MovementController.get().selectedVersion().id();
        }
    }

    private static Component statusMessage(VersionStatus status, String value) {
        String normalized = MovementVersions.normalize(value);
        Component primary = switch (status) {
            case VANILLA -> Component.translatable("legacyparkourcompat.version.status.vanilla")
                .withStyle(ChatFormatting.GRAY);
            case VALID -> Component.translatable("legacyparkourcompat.version.status.implemented", normalized)
                .withStyle(ChatFormatting.WHITE);
            case INVALID -> Component.translatable("legacyparkourcompat.version.status.invalid")
                .withStyle(ChatFormatting.RED);
        };
        ParkourVersion selected = MovementVersions.parkourVersion(normalized);
        if (status == VersionStatus.VALID && selected != null && selected.isPartiallyImplemented()) {
            return primary.copy()
                .append("\n")
                .append(Component.translatable("legacyparkourcompat.version.status.partial")
                    .withStyle(ChatFormatting.YELLOW));
        }
        return primary;
    }

    private static int color(VersionStatus status) {
        return status == VersionStatus.INVALID
            ? TextColor.RED.getValue() | 0xFF000000
            : EditBox.DEFAULT_TEXT_COLOR;
    }

    private int controlWidth() {
        return Math.max(20, Math.min(CONTROL_WIDTH, this.width - 40));
    }

    @Override
    protected void addTitle() {
        this.header = this.layout.addToHeader(LinearLayout.vertical().spacing(4));
        this.header.defaultCellSetting().alignHorizontallyCenter();
        this.header.addChild(new StringWidget(this.title, this.font));

        this.enabledButton = CycleButton.onOffBuilder(this.wanted)
            .create(0, 0, CONTROL_WIDTH, 20, ENABLED, (button, value) -> this.setWanted(value));
        this.enabledButton.active = !this.serverForced;
        this.header.addChild(this.enabledButton);

        this.header.addChild(new StringWidget(FIELD_LABEL, this.font));
        this.versionBox = new EditBox(this.font, 0, 0, CONTROL_WIDTH, FIELD_HEIGHT, FIELD_LABEL);
        this.versionBox.setMaxLength(64);
        this.versionBox.setHint(HINT);
        this.versionBox.setResponder(this::onVersionTyped);
        this.header.addChild(this.versionBox);

        this.statusWidget = new MultiLineTextWidget(Component.empty(), this.font)
            .setMaxWidth(CONTROL_WIDTH)
            .setCentered(true);
        this.header.addChild(this.statusWidget);
        this.header.addChild(new StringWidget(AVAILABLE, this.font));
    }

    @Override
    protected void addOptions() {
    }

    @Override
    protected void addContents() {
        List<ParkourVersion> versions = MovementVersions.listedVersions();
        if (versions.isEmpty()) {
            this.layout.addToContents(new MultiLineTextWidget(NONE_AVAILABLE, this.font)
                .setMaxWidth(CONTROL_WIDTH)
                .setCentered(true));
        } else {
            this.versionLabels = this.layout.addToContents(new VersionLabelsWidget(this, versions));
        }
        this.refreshFieldDisplay();
        this.refreshStatus();
    }

    @Override
    protected void repositionElements() {
        int controlWidth = this.controlWidth();
        if (this.enabledButton != null) {
            this.enabledButton.setWidth(controlWidth);
        }
        if (this.versionBox != null) {
            this.versionBox.setWidth(controlWidth);
        }
        if (this.statusWidget != null) {
            this.statusWidget.setMaxWidth(controlWidth);
        }
        if (this.header != null) {
            this.header.arrangeElements();
            int maxHeader = Math.max(HeaderAndFooterLayout.DEFAULT_HEADER_AND_FOOTER_HEIGHT, this.height - this.layout.getFooterHeight() - 40);
            this.layout.setHeaderHeight(Math.min(this.header.getHeight() + 8, maxHeader));
        }
        super.repositionElements();
        if (this.versionLabels != null) {
            this.versionLabels.updateSize(this.width, this.layout);
        }
    }

    @Override
    protected void setInitialFocus() {
        if (this.serverForced) {
            return;
        }
        if (this.wanted && this.versionBox != null) {
            this.setInitialFocus(this.versionBox);
        } else if (this.enabledButton != null) {
            this.setInitialFocus(this.enabledButton);
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
        MovementVersions.setWanted(this.wanted);
        MovementVersions.setTypedValue(this.draftInput);
    }

    private boolean canEdit() {
        return this.wanted && !this.serverForced;
    }

    private void setWanted(boolean wanted) {
        if (this.serverForced) {
            return;
        }
        this.wanted = wanted;
        this.refreshFieldDisplay();
        this.refreshStatus();
        this.repositionElements();
    }

    private void onVersionTyped(String value) {
        if (this.suppressFieldResponder || !this.canEdit()) {
            return;
        }
        this.draftInput = value;
        this.refreshStatus();
    }

    private void selectListedVersion(ParkourVersion version) {
        if (!this.canEdit()) {
            return;
        }
        this.draftInput = version.id();
        this.writeField(version.id());
        this.refreshStatus();
        this.repositionElements();
    }

    private void refreshFieldDisplay() {
        if (this.versionBox == null) {
            return;
        }
        this.versionBox.setEditable(this.canEdit());
        if (this.serverForced) {
            this.versionBox.setTextColorUneditable(EditBox.DEFAULT_TEXT_COLOR);
            this.writeField(this.draftInput);
            return;
        }
        if (this.wanted) {
            this.versionBox.setTextColor(color(MovementVersions.status(this.draftInput)));
            this.writeField(this.draftInput);
            return;
        }
        this.versionBox.setTextColorUneditable(DISABLED_TEXT_COLOR);
        this.writeField(DISABLED_FIELD.getString());
    }

    private void writeField(String value) {
        this.suppressFieldResponder = true;
        this.versionBox.setValue(value);
        this.suppressFieldResponder = false;
    }

    private void refreshStatus() {
        if (this.versionBox == null || this.statusWidget == null) {
            return;
        }
        if (this.serverForced) {
            Component forced = Component.translatable(
                    "legacyparkourcompat.version.status.forced",
                    this.draftInput)
                .withStyle(ChatFormatting.GOLD);
            ParkourVersion selected = MovementVersions.parkourVersion(this.draftInput);
            if (selected != null && selected.isPartiallyImplemented()) {
                forced = forced.copy()
                    .append("\n")
                    .append(Component.translatable("legacyparkourcompat.version.status.partial")
                        .withStyle(ChatFormatting.YELLOW));
            }
            this.statusWidget.setMessage(forced);
            return;
        }
        if (!this.wanted) {
            this.statusWidget.setMessage(Component.translatable("legacyparkourcompat.version.status.disabled")
                .withStyle(ChatFormatting.GRAY));
            return;
        }
        VersionStatus status = MovementVersions.status(this.draftInput);
        this.versionBox.setTextColor(color(status));
        this.statusWidget.setMessage(statusMessage(status, this.draftInput));
    }

    /**
     * Wrapping version labels. At most five per row, fewer when a name would
     * overlap, with vertical scrolling when they do not fit.
     */
    private static final class VersionLabelsWidget extends AbstractScrollArea {
        private static final int MAX_COLUMNS = 5;
        private static final int COLUMN_GAP = 8;
        private static final int ROW_GAP = 2;

        private final MovementVersionScreen screen;
        private final Font font;
        private final List<ParkourVersion> versions;
        private final int maxLabelWidth;
        private final int rowHeight;
        private int columns = 1;

        VersionLabelsWidget(MovementVersionScreen screen, List<ParkourVersion> versions) {
            super(0, 0, 0, 0, AVAILABLE, defaultSettings(screen.font.lineHeight + ROW_GAP));
            this.screen = screen;
            this.font = screen.font;
            this.versions = List.copyOf(versions);
            int widest = 1;
            for (ParkourVersion version : this.versions) {
                widest = Math.max(widest, this.font.width(version.displayLabel()));
            }
            this.maxLabelWidth = widest;
            this.rowHeight = this.font.lineHeight + ROW_GAP;
        }

        void updateSize(int width, HeaderAndFooterLayout layout) {
            this.setSize(width, layout.getContentHeight());
            this.setPosition(0, layout.getHeaderHeight());
            this.refreshScrollAmount();
        }

        @Override
        public void setSize(int width, int height) {
            super.setSize(width, height);
            this.columns = this.columnsFor(this.width);
            if (this.contentHeight() > this.getHeight()) {
                this.columns = this.columnsFor(this.innerWidth(true));
            }
        }

        private int innerWidth(boolean withScrollbar) {
            return Math.max(1, this.width - (withScrollbar ? this.scrollbarWidth() + COLUMN_GAP : 0));
        }

        private int columnsFor(int innerWidth) {
            int max = Math.min(MAX_COLUMNS, Math.max(1, this.versions.size()));
            for (int candidate = max; candidate >= 1; candidate--) {
                int columnWidth = (innerWidth - COLUMN_GAP * (candidate - 1)) / candidate;
                if (columnWidth >= this.maxLabelWidth) {
                    return candidate;
                }
            }
            return 1;
        }

        private @Nullable ParkourVersion versionAt(double mouseX, double mouseY) {
            if (!this.isMouseOver(mouseX, mouseY) || this.isOverScrollbar(mouseX, mouseY)) {
                return null;
            }
            int innerWidth = this.innerWidth(this.scrollable());
            if (mouseX >= this.getX() + innerWidth) {
                return null;
            }
            int columnWidth = Math.max(1, innerWidth / this.columns);
            int column = (int) ((mouseX - this.getX()) / columnWidth);
            int row = (int) ((mouseY - this.getY() + this.scrollAmount()) / this.rowHeight);
            if (column < 0 || column >= this.columns || row < 0) {
                return null;
            }
            int index = row * this.columns + column;
            if (index < 0 || index >= this.versions.size()) {
                return null;
            }
            return this.versions.get(index);
        }

        @Override
        protected int contentHeight() {
            int rows = Math.max(1, (this.versions.size() + this.columns - 1) / this.columns);
            return rows * this.rowHeight - ROW_GAP;
        }

        @Override
        protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            this.extractScrollbar(graphics, mouseX, mouseY);
            int innerWidth = this.innerWidth(this.scrollable());
            int columnWidth = Math.max(1, innerWidth / this.columns);
            int scroll = (int) this.scrollAmount();
            ParkourVersion hovered = this.versionAt(mouseX, mouseY);
            graphics.enableScissor(this.getX(), this.getY(), this.getX() + innerWidth, this.getBottom());
            for (int index = 0; index < this.versions.size(); index++) {
                ParkourVersion version = this.versions.get(index);
                int column = index % this.columns;
                int row = index / this.columns;
                int x = this.getX() + column * columnWidth;
                int y = this.getY() + row * this.rowHeight - scroll;
                if (y + this.font.lineHeight < this.getY() || y > this.getBottom()) {
                    continue;
                }
                int color = version == hovered && this.screen.canEdit() ? 0xFFFFFFFF : 0xFFAAAAAA;
                graphics.text(this.font, version.displayLabel(), x, y, color, true);
            }
            graphics.disableScissor();
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
            if (this.updateScrolling(event)) {
                return true;
            }
            ParkourVersion version = this.versionAt(event.x(), event.y());
            if (version != null && this.screen.canEdit()) {
                this.screen.selectListedVersion(version);
                return true;
            }
            return this.isMouseOver(event.x(), event.y());
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput output) {
        }

        @Override
        public NarratableEntry.NarrationPriority narrationPriority() {
            return NarratableEntry.NarrationPriority.NONE;
        }
    }
}
