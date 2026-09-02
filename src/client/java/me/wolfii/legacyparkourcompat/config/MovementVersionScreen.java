package me.wolfii.legacyparkourcompat.config;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.config.version.MovementVersions;
import me.wolfii.legacyparkourcompat.config.version.VersionStatus;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.StringWidget;
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
    private static final int MIN_CONTENT_WIDTH = 200;
    private static final int MAX_CONTENT_WIDTH = 360;
    private static final int FIELD_HEIGHT = 20;
    private static final Component TITLE = Component.translatable("legacyparkourcompat.version.title");
    private static final Component ENABLED = Component.translatable("legacyparkourcompat.version.enabled");
    private static final Component FIELD_LABEL = Component.translatable("legacyparkourcompat.version.field");
    private static final Component HINT = Component.translatable("legacyparkourcompat.version.hint");
    private static final Component AVAILABLE = Component.translatable("legacyparkourcompat.version.available");
    private static final Component NONE_AVAILABLE = Component.translatable("legacyparkourcompat.version.available.none");

    private boolean wanted = MovementVersions.isWanted();
    private CycleButton<Boolean> enabledButton;
    private EditBox versionBox;
    private MultiLineTextWidget statusWidget;

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

    private int contentWidth() {
        return Math.clamp(this.width - 40, MIN_CONTENT_WIDTH, MAX_CONTENT_WIDTH);
    }

    @Override
    protected void addOptions() {
    }

    @Override
    protected void addContents() {
        int contentWidth = this.contentWidth();
        int spacing = this.layout.getContentHeight() < 160 ? 4 : 6;
        LinearLayout contents = this.layout.addToContents(LinearLayout.vertical().spacing(spacing));
        contents.defaultCellSetting().alignHorizontallyCenter();

        this.enabledButton = CycleButton.onOffBuilder(this.wanted)
            .create(0, 0, contentWidth, 20, ENABLED, (button, value) -> this.setWanted(value));
        contents.addChild(this.enabledButton);

        LinearLayout field = LinearLayout.vertical().spacing(2);
        field.defaultCellSetting().alignHorizontallyCenter();
        field.addChild(new StringWidget(FIELD_LABEL, this.font));
        this.versionBox = new EditBox(this.font, contentWidth, FIELD_HEIGHT, FIELD_LABEL);
        this.versionBox.setMaxLength(64);
        this.versionBox.setHint(HINT);
        this.versionBox.setValue(MovementVersions.getInput());
        this.versionBox.setResponder(this::onVersionChanged);
        field.addChild(this.versionBox);
        contents.addChild(field);

        this.statusWidget = new MultiLineTextWidget(Component.empty(), this.font)
            .setMaxWidth(contentWidth)
            .setCentered(true);
        contents.addChild(this.statusWidget);

        List<ParkourVersion> versions = MovementVersions.listedVersions();
        if (versions.isEmpty()) {
            contents.addChild(new MultiLineTextWidget(NONE_AVAILABLE, this.font)
                .setMaxWidth(contentWidth)
                .setCentered(true));
        } else {
            contents.addChild(new StringWidget(AVAILABLE, this.font));
            int reservedHeight = 20 + FIELD_HEIGHT + this.font.lineHeight * 4 + spacing * 5;
            int availableHeight = Math.max(this.font.lineHeight, this.layout.getContentHeight() - reservedHeight);
            int gridWidth = Math.max(contentWidth, this.width - 20);
            contents.addChild(new VersionLabelsWidget(
                this.font,
                versions.stream().map(ParkourVersion::displayLabel).toList(),
                gridWidth,
                availableHeight
            ));
        }

        this.refreshInteractiveState();
        this.refreshStatus();
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
     * Non-interactive wrapping labels so every listed version stays on screen.
     */
    private static final class VersionLabelsWidget extends AbstractWidget {
        private static final int COLUMN_GAP = 8;
        private static final int ROW_GAP = 2;

        private final Font font;
        private final List<String> labels;
        private final int rowHeight;
        private int columns;

        VersionLabelsWidget(Font font, List<String> labels, int width, int maxHeight) {
            super(0, 0, width, font.lineHeight, AVAILABLE);
            this.font = font;
            this.labels = List.copyOf(labels);
            this.rowHeight = font.lineHeight + ROW_GAP;
            this.active = false;
            this.relayout(maxHeight);
        }

        private void relayout(int maxHeight) {
            int maxLabelWidth = 1;
            for (String label : this.labels) {
                maxLabelWidth = Math.max(maxLabelWidth, this.font.width(label));
            }
            int byWidth = Math.max(1, (this.width + COLUMN_GAP) / (maxLabelWidth + COLUMN_GAP));
            int maxRows = Math.max(1, maxHeight / this.rowHeight);
            int byHeight = Math.max(1, (this.labels.size() + maxRows - 1) / maxRows);
            this.columns = Math.min(this.labels.size(), Math.max(byWidth, byHeight));
            int rows = Math.max(1, (this.labels.size() + this.columns - 1) / this.columns);
            this.setHeight(rows * this.rowHeight - ROW_GAP);
        }

        @Override
        protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
            int columnWidth = this.columns == 0 ? this.width : this.width / this.columns;
            for (int index = 0; index < this.labels.size(); index++) {
                int column = index % this.columns;
                int row = index / this.columns;
                int x = this.getX() + column * columnWidth;
                int y = this.getY() + row * this.rowHeight;
                graphics.text(this.font, this.labels.get(index), x, y, 0xFFAAAAAA, true);
            }
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput output) {
        }

        @Override
        public NarratableEntry.NarrationPriority narrationPriority() {
            return NarratableEntry.NarrationPriority.NONE;
        }

        @Override
        public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
            return false;
        }

        @Override
        public boolean isMouseOver(double mouseX, double mouseY) {
            return false;
        }
    }
}
