# WORLD-21: pressure-plate, button and landing callbacks

- **1.8.9**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-21-pressure-button-landing-callbacks.md).
- **1.9.4**, ornithe-feather: [source locations in preserved report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-21-pressure-button-landing-callbacks.md).

## Difference

Pressure/button callbacks update world state without direct player movement writes.

Arrow query widening, boat lily-pad removal and HayBlock fall-damage multiplier are excluded.

This does not close downstream redstone/world-state coverage.

## Movement meaning

Pressure plates and buttons update powered block state; arrow/boat branches do not target player movement. The hay landing difference changes the damage multiplier rather than velocity or position.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/WORLD-21-pressure-button-landing-callbacks.md).
