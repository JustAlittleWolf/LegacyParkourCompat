# MD-07: Natural regeneration changes the food input to sprint gating

- **1.8.9**, ornithe-feather: `HungerManager.java`.
- **1.9.4**, ornithe-feather: `HungerManager.java`.

## Difference

At full food with positive saturation and missing health, 1.9.4 restores health more quickly and spends saturation through added exhaustion. 1.8.9 instead uses its slower 80-tick natural-regeneration branch and 3-point exhaustion cost.

This changes when hunger can later cross the shared `foodLevel > 6` sprint threshold.

## Movement meaning

The movement consequence requires normal survival hunger, natural regeneration, damage, and later sprint input.

The sprint condition itself is unchanged; the state feeding it changes through the hunger update.

Hunger is authoritative on the server and is synchronized to the client, whose local sprint gate reads that value.

This finding concerns sprint eligibility only, not the broader health or hunger behavior.

With sustained regeneration from full food and saturation, 1.9.4's exhaustion reaches saturation depletion sooner than 1.8.9's slower healing path, and food can subsequently fall through the shared sprint threshold.

Exact threshold timing depends on health loss, saturation, exhaustion, and other sources of exhaustion; no tick simulation was performed.

[Archived report](../../archive/legacy-workflows/movement-discovery/runs/1.8.9--1.9.4/findings/MD-07-hunger-regeneration-sprint-gate.md).
