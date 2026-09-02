package me.wolfii.legacyparkourcompat.config;

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

import java.util.List;

public class MovementVersionScreen extends OptionsSubScreen {
    private static final int CONTROL_WIDTH = 310;
    private static final int FIELD_HEIGHT = 20;
    private static final Component TITLE = Component.translatable("legacyparkourcompat.version.title");
    private static final Component ENABLED = Component.translatable("legacyparkourcompat.version.enabled");
    private static final Component FIELD_LABEL = Component.translatable("legacyparkourcompat.version.field");
    private static final Component HINT = Component.translatable("legacyparkourcompat.version.hint");
    private static final Component AVAILABLE = Component.translatable("legacyparkourcompat.version.available");
    private static final Component NONE_AVAILABLE = Component.translatable("legacyparkourcompat.version.available.none");

    private boolean wanted = MovementVersions.isWanted();
    private LinearLayout header;
    private CycleButton<Boolean> enabledButton;
    private EditBox versionBox;
    private MultiLineTextWidget statusWidget;
    private VersionLabelsWidget versionLabels;

    public MovementVersionScreen(Screen lastScreen) {
        super(lastScreen, Minecraft.getInstance().options, TITLE);
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
        this.header.addChild(this.enabledButton);

        this.header.addChild(new StringWidget(FIELD_LABEL, this.font));
        this.versionBox = new EditBox(this.font, 0, 0, CONTROL_WIDTH, FIELD_HEIGHT, FIELD_LABEL);
        this.versionBox.setMaxLength(64);
        this.versionBox.setHint(HINT);
        this.versionBox.setValue(MovementVersions.getInput());
        this.versionBox.setResponder(this::onVersionChanged);
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
            this.versionLabels = this.layout.addToContents(new VersionLabelsWidget(
                this.font,
                versions.stream().map(ParkourVersion::displayLabel).toList()
            ));
        }
        this.refreshInteractiveState();
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
        if (this.wanted && this.versionBox != null) {
            this.setInitialFocus(this.versionBox);
        } else if (this.enabledButton != null) {
            this.setInitialFocus(this.enabledButton);
        }
    }

    private void setWanted(boolean wanted) {
        this.wanted = wanted;
        MovementVersions.setWanted(wanted);
        this.refreshInteractiveState();
        this.refreshStatus();
        this.repositionElements();
    }

    private void onVersionChanged(String value) {
        MovementVersions.setTypedValue(value);
        this.refreshStatus();
        this.repositionElements();
    }

    private void refreshInteractiveState() {
        if (this.versionBox != null) {
            this.versionBox.setEditable(this.wanted);
            this.versionBox.active = this.wanted;
        }
    }

    private void refreshStatus() {
        if (this.versionBox == null || this.statusWidget == null) {
            return;
        }
        if (!this.wanted) {
            this.versionBox.setTextColor(EditBox.DEFAULT_TEXT_COLOR);
            this.statusWidget.setMessage(Component.translatable("legacyparkourcompat.version.status.disabled")
                .withStyle(ChatFormatting.GRAY));
            return;
        }
        String value = this.versionBox.getValue();
        VersionStatus status = MovementVersions.status(value);
        this.versionBox.setTextColor(color(status));
        this.statusWidget.setMessage(statusMessage(status, value));
    }

    /**
     * Non-interactive wrapping labels. At most five per row, fewer when the
     * longest name would overlap, with vertical scrolling when they do not fit.
     */
    private static final class VersionLabelsWidget extends AbstractScrollArea {
        private static final int MAX_COLUMNS = 5;
        private static final int COLUMN_GAP = 8;
        private static final int ROW_GAP = 2;

        private final Font font;
        private final List<String> labels;
        private final int maxLabelWidth;
        private final int rowHeight;
        private int columns = 1;

        VersionLabelsWidget(Font font, List<String> labels) {
            super(0, 0, 0, 0, AVAILABLE, defaultSettings(font.lineHeight + ROW_GAP));
            this.font = font;
            this.labels = List.copyOf(labels);
            int widest = 1;
            for (String label : this.labels) {
                widest = Math.max(widest, font.width(label));
            }
            this.maxLabelWidth = widest;
            this.rowHeight = font.lineHeight + ROW_GAP;
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
            int max = Math.min(MAX_COLUMNS, Math.max(1, this.labels.size()));
            for (int candidate = max; candidate >= 1; candidate--) {
                int columnWidth = (innerWidth - COLUMN_GAP * (candidate - 1)) / candidate;
                if (columnWidth >= this.maxLabelWidth) {
                    return candidate;
                }
            }
            return 1;
        }

        @Override
        protected int contentHeight() {
            int rows = Math.max(1, (this.labels.size() + this.columns - 1) / this.columns);
            return rows * this.rowHeight - ROW_GAP;
        }

        @Override
        protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            this.extractScrollbar(graphics, mouseX, mouseY);
            int innerWidth = this.innerWidth(this.scrollable());
            int columnWidth = Math.max(1, innerWidth / this.columns);
            int scroll = (int) this.scrollAmount();
            graphics.enableScissor(this.getX(), this.getY(), this.getX() + innerWidth, this.getBottom());
            for (int index = 0; index < this.labels.size(); index++) {
                int column = index % this.columns;
                int row = index / this.columns;
                int x = this.getX() + column * columnWidth;
                int y = this.getY() + row * this.rowHeight - scroll;
                if (y + this.font.lineHeight < this.getY() || y > this.getBottom()) {
                    continue;
                }
                graphics.text(this.font, this.labels.get(index), x, y, 0xFFAAAAAA, true);
            }
            graphics.disableScissor();
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
            return this.updateScrolling(event);
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
