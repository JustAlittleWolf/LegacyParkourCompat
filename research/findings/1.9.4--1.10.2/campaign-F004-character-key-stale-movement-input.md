# F004: Character-code key binding polling differs in 1.9.4 and 1.10.2

- **1.9.4**, ornithe-feather: `KeyBinding.java`; lines 40-47.
- **1.10.2**, ornithe-feather: `KeyBinding.java`; lines 40-47.

## Difference

1.9.4 polling exception can retain a pressed state while 1.10.2 clears codes >=256.

## Movement meaning

KeyboardInput reads bindings into movement axes. Screen entry releases those bindings, so the proposed chat-return path does not supply the differing pressed state needed to affect player movement.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.9.4--1.10.2/findings/F004-character-key-stale-movement-input.md).
