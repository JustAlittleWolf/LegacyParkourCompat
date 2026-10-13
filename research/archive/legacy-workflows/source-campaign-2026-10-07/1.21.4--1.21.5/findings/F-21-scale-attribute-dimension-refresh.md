# F-21: Player scale attribute changes refresh dimensions only in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S2-DIMENSIONS, S2-EYE-HEIGHT, S2-COLLISION, S2-STATE-WRITERS
- Classification: changed behavior
- Confidence: source-confirmed; independent snapshot review pending
- Applicability: player movement when the live `Attributes.SCALE` value changes after player construction and before another dimension refresh
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

### A artifact identity

- Evidence artifact record ID: A1
- Publication status: original-verified
- Immutable evidence path: `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap/`
- Evidence artifact SHA-256: source manifest `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; artifact manifest `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`
- Evidence manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.21.4/mojmap.sources.sha256` / `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.21.4/artifacts.sha256` / `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`
- Original derived-artifact availability and SHA-256: source and mapped client jar available; jar SHA-256 `56995548c9cb8bd7cdb9996b676daeafae02bde9d6ef4029b9eeca6bfd7dcc74`
- Source/raw-input hash relation: readiness marker identifies exact release and source manifest above; cited source file hashes rechecked against that manifest.
- Provenance limitations: none identified for the cited publication.
- Source files / member ranges / SHA-256:
  - `net/minecraft/world/entity/LivingEntity.java`; `createLivingAttributes()` lines 317-335, `getScale()` lines 541-548, `refreshDirtyAttributes()` lines 1048-1056, `onAttributeUpdated(Holder<Attribute>)` lines 1058-1071, and dirty-attribute caller in `aiStep()` line 2550; SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`.
  - `net/minecraft/world/entity/player/Player.java`; `createAttributes()` lines 229-242; SHA-256 `c45f41b9784ce50a88a498e84a13edcef0a23e175233d726ed7f90940f19ebc1`.
  - `net/minecraft/world/entity/ai/attributes/Attributes.java`; `SCALE` registration line 73; SHA-256 `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea`.
  - `net/minecraft/server/commands/AttributeCommand.java`; base-value mutation path, `setBaseValue` at line 301; SHA-256 `57a8216ea702ccd42deac44c429141a5268819242cadc436df9bdf9146427d92`.
  - `net/minecraft/world/entity/Entity.java`; `refreshDimensions()` lines 2948-2964; SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`.

### B artifact identity

- Evidence artifact record ID: B1
- Publication status: original-verified
- Immutable evidence path: `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap/`
- Evidence artifact SHA-256: source manifest `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; artifact manifest `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`
- Evidence manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.21.5/mojmap.sources.sha256` / `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`
- Original artifact-manifest path / SHA-256: `build/movement-campaign-2026-10-07/ready/1.21.5/artifacts.sha256` / `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`
- Original derived-artifact availability and SHA-256: source and mapped client jar available; jar SHA-256 `124561e91a61714ca5a73495784c14c03a7a927d1f715eafbcb56ae115a6712a`
- Source/raw-input hash relation: readiness marker identifies exact release and source manifest above; cited source file hashes rechecked against that manifest.
- Provenance limitations: none identified for the cited publication.
- Source files / member ranges / SHA-256:
  - `net/minecraft/world/entity/LivingEntity.java`; `createLivingAttributes()` lines 306-324, `getScale()` lines 517-524, `refreshDirtyAttributes()` lines 1067-1075, `onAttributeUpdated(Holder<Attribute>)` lines 1077-1091, and dirty-attribute caller in `aiStep()` line 2570; SHA-256 `a8aed863d4fdc515c751dd2878a8bb9c13179228cb8dbb50edf1d19cd5404271`.
  - `net/minecraft/world/entity/player/Player.java`; `createAttributes()` lines 244-257; SHA-256 `8fc187f33999db9dfc49251e95e92a17645a50adab16ca0f93f4948209f62036`.
  - `net/minecraft/world/entity/ai/attributes/Attributes.java`; `SCALE` registration line 73; SHA-256 `14bcc5821d0f67f41fdc3e512556181823cff5c43f84fc1007e51d25f413a3ea`.
  - `net/minecraft/server/commands/AttributeCommand.java`; base-value mutation path, `setBaseValue` at line 301; SHA-256 `57a8216ea702ccd42deac44c429141a5268819242cadc436df9bdf9146427d92`.
  - `net/minecraft/world/entity/Entity.java`; `refreshDimensions()` lines 2941-2957; SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.

## Source-level difference

Both releases register `Attributes.SCALE` in `LivingEntity.createLivingAttributes()`, and `Player.createAttributes()` extends that builder. `LivingEntity#getScale()` reads the live attribute value. The paired `AttributeCommand` can set an entity attribute's base value with `AttributeInstance#setBaseValue`, establishing a vanilla player-reachable mutation path; dirty attributes are processed by `LivingEntity#refreshDirtyAttributes()`, which invokes `onAttributeUpdated`.

In A, `LivingEntity#onAttributeUpdated` handles maximum health and absorption only. In B, it additionally calls `refreshDimensions()` when the changed attribute is `Attributes.SCALE`. That shared refresh stores `getDimensions(currentPose)` and its eye height before updating the bounding box; D36 records the paired writer and its operation order. Consequently, after a live player scale change is processed while pose remains unchanged, B refreshes the stored dimensions, bounding box and eye height, while A retains the previous stored dimensions and eye height until another refresh path runs. The changed state is directly movement-relevant through player collision dimensions and eye-based fluid predicates; no trajectory is claimed.

## Reachability and dependencies

Player attribute builder -> inherited `Attributes.SCALE` registration -> vanilla attribute command base-value update -> dirty-attribute processing -> `LivingEntity#onAttributeUpdated` -> version-specific dimension refresh. This finding is limited to a scale mutation after the player's dimensions have been established, with no intervening pose or other refresh that would mask the callback difference. The paired scale declaration, Player attribute-builder inheritance, mutation path, dirty dispatcher, callback and downstream dimensions writer are closed for this condition. Broader scale writers and the overall player dimension/movement inventory remain open.

## Consequence and uncertainty

The source proves a difference in when the cached player dimensions and eye height refresh after a scale attribute update. In A, later direct `getScale()` reads return the new attribute while the stored bounding box/eye height can remain at the old scale; in B, the dirty callback refreshes them. Subsequent collision, movement bounds and eye-height fluid sampling can therefore observe different state. The exact movement consequence depends on the attribute value, timing, pose, world contacts and subsequent movement. Runtime validation was not performed.

## Handoff

Independent delta: 1.21.5 refreshes player dimensions on the inherited scale-attribute update callback; 1.21.4 does not. Snapshot review is pending. Pair remains partial, and this finding does not close S2/S3 inventories.
