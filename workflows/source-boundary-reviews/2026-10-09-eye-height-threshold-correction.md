# Correction memo: cached eye-height threshold boundary

**Prepared:** 2026-10-09  
**Scope:** Source-only correction for the exact-float boundary wording in one reviewed 1.21.11–26.1.2 checkpoint. This memo records a correction; it is not a new movement finding, implementation decision, or review acceptance.

## Immutable records bound

- Prior independent review: commit `86a47f1784d57c6374a8ca28c401e29a4b9a4bec`; path `workflows/source-boundary-reviews/2026-10-09-eye-fluid-checkpoint-review.md`; Git blob `b93a40a76200975da2b2cd02962a4347849ee8cc`. Its disposition was REQUEST CHANGES only for the “exactly-0.4” cached-float wording. Preserve this review unchanged.
- Checkpoint specifically reviewed: commit `a0716b793aedb9018a9c42aac5845bad852d8704`; path `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/run.md`; Git blob `42612691277298a756f0dacd5785d66f1062fa21`. Preserve that checkpoint snapshot unchanged.
- The corrected working ledger is the later source-only report at the task branch tip. Its prior text correction is recorded in commit `52be104f180f994d9a2e83e0ca9c782f9c926ba1`; the present memo gives the correction its own immutable artifact identity when committed.

## Verified source arithmetic

Both exact source bodies use the same method expression:

    return this.getEyeHeight() < 0.4 ? 0.0 : 0.4;

A (1.21.11 Mojmap) Entity.getFluidJumpThreshold(), lines 3589–3591; B (26.1.2 native unobfuscated), lines 3614–3616. Entity.getEyeHeight() returns the cached float eyeHeight (A 3297–3299; B 3393–3395). Java promotes that float to double for comparison with the unsuffixed double literal 0.4.

No float value widens to exactly 0.4D. The two adjacent representable floats around the literal are:

- 0.3999999761581421F (0x3ecccccc), which is less than 0.4D and selects 0.0D.
- 0.4000000059604645F (0x3ecccccd, the value written by 0.4F), which is greater than 0.4D and selects 0.4D.

Thus the prior “including the exactly-0.4 case” statement is invalid; there is no equality case for this cached-float input. The paired strict comparison and resulting branches are otherwise identical.

The vanilla Player input is also now bounded in the paired source. EntityType.PLAYER registration explicitly sets .eyeHeight(1.62F) (A EntityType.java 1191–1200; B 1189–1198). Base Entity.getDimensions(Pose) obtains the type dimensions, pose refresh copies dimensions.eyeHeight() into cached eyeHeight, and neither paired Player source overrides getDimensions(Pose); construction initializes the same cached value. Therefore vanilla Player has cached 1.62F after construction and pose refresh in both releases. Widened to double, it is 1.6200000047683716D, so the threshold method returns 0.4D for vanilla Player. This closes only the vanilla Player eye-height provider for the cited releases, not other entities or external state producers.

## Consumer evidence and remaining scope

The paired LivingEntity.travelInLava() bodies preserve the same order: relative movement, SELF move, lava-height <= threshold branch, low-height damping/fall adjustment or high-height half-scale, gravity quarter-step, then jumpOutOfFluid (A 2409–2427; B 2496–2514). The paired LivingEntity.aiStep() fluid-jump branch also reads fluid height and this threshold under jumping && isAffectedByFluids() (A 2933–2950; B 3032–3052). Player's fluid-affect gate is !abilities.flying on each side (A Player.java 884–886; B 875–877). These are bounded source-body traces, not closure of all consumer inputs or proof of runtime parity.

Keep these routes open:

- Mounted applicability, including boat/vehicle states, mounted eye gates and mounted collision boxes beyond the exact F-4 mounted Nether LAVA snapshot.
- Fluid-height producers, fluid state/level combinations, loaded-region and world inputs, and full movement-resource/tag dependency closure (D2 remains open).
- The separate eye-top predicate witness and its sustained Player position, prior-eye tick ordering, swimming write, and movement consequence.
- Remaining external position/state writers, full S1/S7 caller and input inventories, and dependency closure across the pair.
- Full-pair audit/freeze, first changed release determination, and runtime validation; none is established by this memo.

F-4 remains a distinct independently accepted mounted Nether LAVA snapshot only; it does not close the above threshold or pair-wide routes. No implementation or runtime claim is made here.

## Source identities

Exact source-file SHA-256 identities checked for this memo:

| Source | A SHA-256 | B SHA-256 |
|---|---|---|
| world/entity/Entity.java | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| world/entity/LivingEntity.java | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| world/entity/player/Player.java | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| world/entity/EntityType.java | `fc2abb3e905b9a63a27f0b027fad089d9d40e5d3d45608406b5da2a5ec47a73e` | `e5f37ee2dd639b7271197e58f7a6f3bdbedc0f0f1d618039d159a0e7de315229` |

No build, tests, runtime, client/server, TAS, Docker, wiki/MCPK, or main merge was performed.
