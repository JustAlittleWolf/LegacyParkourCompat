# Independent source audit: player equipment to movement attributes

## Verdict

**ACCEPT** the bounded source report, bound to current report SHA-256 `368F1038CF3075982C0C5335F0E53C21BDE0CEF28AB4A25A765AC5DC93B1ACAC` at `build/later-source-20261116/1.21.11--26.1.2/equipment_attribute_path/coverage.md`.

The report's ordered client equipment and inventory/menu writes, server-only equipment modifier collection, dirty-attribute publication, client attribute snapshot replacement, and listed movement-attribute readers are supported by the exact paired sources. The source identities are correct: 1.21.11 Mojmap uses root sidecar `decompiled_minecraft/1.21.11/mojmap.provenance.json`; 26.1.2 published unobfuscated uses `decompiled_minecraft/26.1.2/unobfuscated.provenance.json`. Their declared version IDs/namespaces, ready statuses, and ready-bound source-manifest digests match the report.

## Evidence checks

- Recomputed the report hash after the author's scope correction. It is bound to the current bytes above; this supersedes the initial audit assignment's r1 digest. The correction removes the `SAFE_FALL_DISTANCE` fall-damage threshold candidate, which is outside movement scope.
- Verified all 15 Java paths on both endpoints (30 current files total) against the report's SHA-256 table and each endpoint's source-manifest row; zero mismatches.
- Rechecked the 36–42 equipment-slot map and `Inventory.setItem` writes; default inventory-menu armor/offhand slots and `ArmorSlot`/`Slot` write ordering; client equipment and player-inventory packet targets; and the distinction between equipment stack writes and generic open-menu containers.
- Rechecked `LivingEntity.tick` gates `detectEquipmentUpdates` under `!level().isClientSide()`, changed-slot removal then new-stack modifier enumeration, component/enchantment slot filtering, and transient attribute add/remove order. `AttributeInstance` modifier insertion/removal and cached calculation operations are equivalent in the inspected methods.
- Rechecked `ServerEntity.sendDirtyEntityData` dirty-attribute send/clear order and `ClientPacketListener.handleUpdateAttributes` target validation, base-value write, modifier clearing, and transient snapshot writes. The listed movement-attribute consumers/default declarations match in the compared source locations.

## Limits

Acceptance is limited to the report's stated Java-source routes. It does not establish particular packaged item/component or enchantment values, effective item membership, packet ordering between equipment and attribute snapshots, all inventory/menu effects, complete tick/provider closure, or runtime parity. The pair remains partial; no pair freeze is granted.

No product/backlog/wiki/Git-history review, artifact inspection, build, test, or runtime check was performed.
