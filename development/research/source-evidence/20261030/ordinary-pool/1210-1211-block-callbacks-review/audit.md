# Bounded source audit: 1.21.0 → 1.21.1 block callbacks

## Verdict

Accepted within the stated ordinary local-player collision/support scope. I found no false claim or open source gate among the four `onRemove` reorderings and the block-entity validation path. All 22 source-file hashes listed by the closure match the canonical read-only trees.

The decisive side gate is `LevelChunk.setBlockState`: both versions call the old state's `onRemove` only on the logical server; the client branch removes an old block entity on block-identity change and does not invoke that callback. The four callback methods also guard on block identity changing. Their reordered notifications can affect server neighbor/comparator processing and later delivered states, but not a client's direct callback response for equal received block states.

The 1.21.1 validation checks the entity type's valid-block membership. Property-only changes preserve membership; block changes remove the old entity in the relevant server/client transition paths. The shulker-box type accepts its color variants, and its support/collision shape readers are unchanged. A stale or malformed entity already attached to a shulker state remains an explicitly excluded edge; this audit does not generalize the ordinary-path result to it.

## Identity and remaining gates

- Closure SHA-256: `b7dbc5af7fcad4b3e8326ecbc2888c7ec8e72f593d153c1180f57394bacda5e0`
- Canonical source identity: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.21,1.21.1}/mojmap`; the 22 listed hashes for the four blocks, `BlockEntity`, `LevelChunk`, `BlockBehaviour`, `BlockEntityType`, `ShulkerBoxBlockEntity`, `ClientPacketListener`, and `ClientLevel` all matched.
- Remaining scope gates: malformed/external stale block entities; other changed source paths; the complete 1.21.0–1.21.1 movement pair. These were not closed by this bounded audit.
