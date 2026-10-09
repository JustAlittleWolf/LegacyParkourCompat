# Supplemental review: standing Player cached eye-height provider

**Review date:** 2026-10-09
**Disposition:** **ACCEPTED for the bounded vanilla default standing-Player provider path.** The earlier exact-float correction acceptance remains unchanged. This does not close pair coverage or other eye-height providers.

## Immutable records and exact source publications

- Prior correction review: `143613bf98c7d4dc245bc58fb1ad9cc4cdb90ebf`, `workflows/source-boundary-reviews/2026-10-09-eye-height-threshold-correction-review.md`.
- Corrected memo: `ff956771f753f8d2a92c2fb3e51caebbfaa7f4a2`, `workflows/source-boundary-reviews/2026-10-09-eye-height-threshold-correction.md`, blob `2f890681c6bc6026034710417a3c0265ca8087e3`.
- Memo binding in the pair ledger: `51f414abd08ec1dc247c7a4af9e1a52507094b89`, pair `run.md` blob `4cf067ae7f105023f03fc9349aa870418a506cc6`.
- Canonical read-only source root: `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready`.

| Side | Resolved source directory / namespace | Ready-marker SHA-256 | Source-manifest SHA-256 | Artifact-manifest SHA-256 |
|---|---|---|---|---|
| A: 1.21.11 | `1.21.11/mojmap` / Mojmap | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |
| B: 26.1.2 | `26.1.2/unobfuscated` / native unobfuscated | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |

The ready markers report exact versions, mapping paths, and `ready` status. I recomputed the marker, source-manifest, and artifact-manifest hashes; they match the marker values. Each cited source file's SHA-256 also matches its row in the corresponding source manifest.

| Source path relative to each source directory | A SHA-256 | B SHA-256 |
|---|---|---|
|
et/minecraft/world/entity/EntityType.java` | `fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e` | `e5f37ee2dd639b7271197e58f7a6f3bdbedc0f0f1d618039d159a0e7de315229` |
|
et/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
|
et/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
|
et/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
|
et/minecraft/world/entity/EntityDimensions.java` | `9fb9575ec615d4aefe6316c443e87a902f44e955393778a4b93623e8b680adc4` | `27fa1fb5c3e9bf49123a238675f6bea29e65ad5b0362a9a64dcb5d5f5407376a` |
|
et/minecraft/world/entity/ai/attributes/Attributes.java` | `62129028c2f7beb6d2f87bcd3345e63a7be8fc3950a7ecdbc6ee21f8fb89f843` | `8eee57d8375c7af39525ccd25593618fcbcbe0348da6a8738556baaabea3c1d5` |
|
et/minecraft/world/entity/ai/attributes/DefaultAttributes.java` | `5af4872c7e21005d7bb3932856c6aca4a9d877eeb2bd06f6100f9e53fd40cd44` | `8d68376eacb084b5acea9b8d2e413e698aa30bbb8c2b4088f56bbc088e959e3a` |

## Verified effective provider path

1. `EntityType.PLAYER` registers `.eyeHeight(1.62F)` in A at `EntityType.java:1191-1200` (value at 1197) and B at `EntityType.java:1189-1198` (value at 1195). The builder's `eyeHeight(float)` writes the value into the registered `EntityDimensions`.
2. The `Entity` constructor obtains the type's dimensions and initializes cached `eyeHeight` from `dimensions.eyeHeight()` (A `Entity.java:289-308`; B `Entity.java:303-322`). Thus initial cached eye height is `1.62F` on both sides.
3. On a pose refresh, `Entity.refreshDimensions()` stores `getDimensions(pose)` and copies its eye height into the cache (A `Entity.java:3226-3232`; B `Entity.java:3320-3326`). The effective Player implementation is the final override in `LivingEntity.getDimensions(Pose)`, not the base `Entity.getDimensions(Pose)` summarized in the memo. `LivingEntity` returns sleeping dimensions for `SLEEPING`; for the standing route it uses `getDefaultDimensions(pose).scale(getScale())`. `getDefaultDimensions` obtains the type dimensions scaled by `getAgeScale()` (A `LivingEntity.java:3561-3567`; B `LivingEntity.java:3672-3678`). `Player.java` has no `getDimensions(Pose)` override in either source; its matching `getDimensions` text is only a call site.
4. `LivingEntity.isBaby()` returns false, and `getAgeScale()` therefore returns `1.0F` (A `LivingEntity.java:531-537`; B `LivingEntity.java:527-533`). Player has no `isBaby()` override. `LivingEntity.getScale()` reads `Attributes.SCALE`; the Player attribute builder inherits `LivingEntity.createLivingAttributes()`, which includes SCALE; and `DefaultAttributes` binds `EntityType.PLAYER` to `Player.createAttributes().build()` (A rows at `LivingEntity.java:325-334`, `Player.java:211-214`, `DefaultAttributes.java:150`; B at `LivingEntity.java:318-327`, `Player.java:205-208`, `DefaultAttributes.java:150`). `Attributes.SCALE` has base value `1.0` in both sources (`Attributes.java:76-78`). With default vanilla Player attributes and no scale modifier, `getScale()` is `1.0F`.
5. `EntityDimensions.scale(float)` scales `eyeHeight` by its height factor and returns the original dimensions when the factors equal `1.0F` (A `EntityDimensions.java:25-32`; B `EntityDimensions.java:25-37`). Therefore both the age-scale and default attribute-scale steps preserve the Player type's `1.62F` eye height for a standing pose. The refresh writes `1.62F` back to cached `eyeHeight`.

The memo's stated numeric witness is therefore correct for a vanilla default Player after construction and a standing-pose refresh. The effective `LivingEntity` override is an essential part of the evidence path and is recorded here to refine the memo's abbreviated `Entity.getDimensions(Pose)` description.

## Disposition and remaining scope

The supplemental standing-Player provider witness is independently verified for these exact releases and default attributes. This closes only that named route. A non-default scale attribute, sleeping pose, other entity/provider, mounted or boat applicability, loaded-region/fluid inputs, the eye-top predicate/consequence, remaining S1/S7 dependencies, pair-wide audit/freeze, first-changed-release determination, and runtime validation remain outside this bounded acceptance and open as before. The prior exact-float correction acceptance and all earlier paired-body/order decisions are unchanged.

**Resume:** continue the source-only pair ledger's remaining open coverage/dependency rows; do not infer pair completion from this supplemental witness.

No source files were changed. No decompile, full-body re-review, new discovery, build, tests, runtime, main merge, or push was performed.