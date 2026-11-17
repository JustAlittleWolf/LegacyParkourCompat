# A sole Beacon neighbor adds a wall collision arm in 1.14

Exact 1.13, 1.13.1 and 1.13.2 exclude Beacon when a dry cobblestone wall decides whether to connect. Exact 1.14 and 1.14.4 include it, as does 26.2. The bounded fixture has a single loaded Beacon at a horizontal neighboring cell and ordinary loaded air at the wall's other three horizontal neighbors and above it.

In Feather 1.13.2 `WallBlock.getPlacementState` (69–92) and its update route (100–116) query the opposing neighbor face. `shouldConnectTo` (51–65) applies `Block.isExceptionForAttachment`, which delegates to `isExceptionForPlacement` and explicitly excludes Beacon (`Block.java:241–256`). Air supplies no connection on the remaining sides. The historical wall has a post and zero arms. The same wall body and Beacon exception apply in the other exact 1.13 releases.

Exact Feather 1.14 and 1.14.4 apply the full-face test and omit Beacon from the exception list. Its inherited full block collision face connects. With air above and one neighbor, the resulting wall has a raised post and the one horizontal arm. Exact 26.2's full support-face check likewise gives `UP=true` and the Beacon side `LOW`. The collision constructor uses a 24/16 height for both that arm and the post.

For the measured source fixture, place the wall at (0,64,0) and Beacon at (0,64,-1). The centered post has local X/Z [4,12]/16 and the north arm has X [5,11]/16, Z [0,11]/16. A width-.6 player centered at X=.5, Z=-.1 with feet Y=65.65 clears the post and would overlap the arm on a pure vertical SELF request of about -.3. The older path reaches feet Y≈65.35, still above Beacon's Y=65 top. The checked later path clips at feet Y=65.5. Because X and Z displacement are zero, the compared step-up gates do not run.

Exact Yarn 1.14.1, 1.14.2 and 1.14.3 retain the same wall connection to Beacon. The [patch alias review](../../source-evidence/20261117/block-b-solid-collision/beacon-wall-vertical-landing/114-patch-alias-review.md) verifies the hidden source provenance and the wall's full collision-face predicate, Beacon exclusion list, and collision shape.

This source result covers the specified ordinary collision fixture. Other neighbor sets, state mutations, packet/server effects and measured runtime parity remain separate.

[Accepted proof](../../source-evidence/20261117/block-b-solid-collision/beacon-wall-vertical-landing/proof-r2.md) · [Independent review](../../source-evidence/20261117/block-b-solid-collision/beacon-wall-vertical-landing/verdict.md) · [Source binding](../../source-evidence/20261117/block-b-solid-collision/beacon-wall-vertical-landing/binding.md)
