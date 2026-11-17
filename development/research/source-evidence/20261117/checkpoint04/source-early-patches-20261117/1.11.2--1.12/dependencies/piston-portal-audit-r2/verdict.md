# Bounded source audit r2: piston and portal wording correction

Verdict: **ACCEPT** — the narrow portal-gating correction is supported. This accepts only the revised wording bound below; it does not establish pair coverage or pair freeze.

## Audited inputs

- Correction report: `build/source-early-patches-20261117/1.11.2--1.12/dependencies/piston-portal-player-response-r2.md`
- Correction SHA-256: `84f5160938a961bcd3de8bab9f066b9909ec1162bf4055de37ca6f53224d920b`
- Original report SHA-256: `082832cff9c53cff9cbebd3a0bfed17b483e8915525031855393abd415942725`
- Prior REQUEST CHANGES verdict SHA-256: `7d686b6303db08e8e1c077165605c99281161ec933d9faa529a9d4d50d078243`
- Pair: `decompiled_minecraft/1.11.2/ornithe-feather/` → `decompiled_minecraft/1.12/ornithe-feather/`

## Correction disposition

In both exact `net/minecraft/entity/Entity.java` sources, `onPortalCollision(BlockPos)` sets `inPortal = true` in the `portalCooldown <= 0` branch after the inner condition. Only `lastPortalPos`, `lastPortalOffset`, and `lastPortalFacing` capture is guarded by `!world.isClient && !pos.equals(lastPortalPos)`. The r2 report states this distinction accurately. Thus `inPortal` may be set on the logical client, while the portal location/offset/facing calculation is server-side and only repeated for a changed portal position.

The corrected report does not claim a direct client position or velocity update. It keeps server-side dimension transfer/position setup separate from local-client movement response and explicitly leaves client packet application unassessed. No piston/no-difference claim was reopened or re-audited here.

This verdict is bounded to the corrected gate description and its claim qualification. It does not accept the report as full pair coverage, close unrelated dependencies, or freeze the pair.