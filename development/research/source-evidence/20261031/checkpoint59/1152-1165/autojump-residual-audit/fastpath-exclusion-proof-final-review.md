# Final independent review: auto-jump full-cube fast-path exclusion

## Verdict: ACCEPT — bounded to the listed pose dimensions

Bound to `.native-source/20261030/1152-1165-finish43/autojump-residual-audit/fastpath-exclusion-proof.md`, SHA-256 `1d0b2932885cfcafa78ac27d433158d1227a7f75c1f72b2acb4cf2bc17a50d6c`, and collision residual report SHA-256 `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c`.

The revised proof closes only the named raw-AABB versus rounded-`Shapes.create(AABB)` full-cube fast-path dependency when the current player box has any of these vanilla dimensions: standing, crouching, fall-flying, swimming, or spin attack. It explicitly leaves sleeping and dying (height 0.2) unresolved. I accept that narrower result; this is not a full `updateAutoJump` or pair-closure verdict.

## Independent math/source checks

The source ordering is correct. Old 1.15.2 `LocalPlayer.java:905` and new 1.16.5 `:917` construct the broadphase before adding `0.51F` to either segment endpoint; the lateral vector and segment endpoint construction follow at old `:906–913` and new `:918–925`. The broadphase horizontal bounds include the swept centerline bounds plus `s` at each side. The tested segments are offset by `s/2` times the horizontal perpendicular components.

`vec34` is normalized by one float Newton-refined inverse-square-root call, so the proof correctly uses a conservative length factor rather than exact real normalization. The exact source implementation is `Mth.fastInvSqrt(float)` at 1.15.2 `Mth.java:394–400` and 1.16.5 `:374–380`: bit-hack seed followed by `y * (1.5F - (0.5F * x * y * y))`. For positive finite float inputs above `0.001F`, the conservative `[0.99,1.01]` length-factor interval is valid. An exhaustive mantissa check of the two exponent-parity classes at the lowest applicable normal exponent (117/118), followed by the same exponent-scaling relation for the remaining normal exponents, gave relative factors from approximately `0.99824766` through `1.00000014`; this is comfortably within the stated bound. The float conversion of the measured squared length does not approach the 1% allowance for the listed finite local movement values.

Thus, for `s=0.6`, each horizontal query overlap at a side-segment hit is at least `s - 1.01s/2 = 0.297`. With vertical line height `playerY+0.51` and listed heights `t>=0.6`, a unit block interval containing the line point overlaps the query by at least `min(t-0.51,0.51,1)`, which is at least `0.09`.

The exact `Shapes.findBits` source uses endpoint tolerance `<1.0E-7` after multiplying by a resolution `2^i`, `i=0..3`; any accepted endpoint rounding is therefore `<1.0E-7/2^i`, and globally `<1.0E-7`. The segment-hit query margins above exceed that bound, so a candidate in the rounded-away fringe cannot also hit either tested segment for these pose dimensions.

The supported pose dimensions are paired in the source. `Player.POSES` gives width 0.6 for STANDING, CROUCHING, FALL_FLYING, SWIMMING and SPIN_ATTACK, with heights 1.8, 1.5, 0.6, 0.6 and 0.6. `LivingEntity.SLEEPING_DIMENSIONS` and the Player pose table give SLEEPING and DYING dimensions 0.2×0.2; the proof correctly leaves those out of scope. No change to those dimensions is claimed here.

One phrase in the proof calls the perpendicular vector “unit” before applying the `[0.99,1.01]` bound. Read the later conservative bound as governing the calculation; replacing “unit” with “approximately unit, with component magnitude at most 1.01” would remove that small wording inconsistency, but it does not change the accepted inequality or scoped verdict.

## Verified evidence fingerprints

Every relevant source matched its own manifest. Exact source-manifest hashes: 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; 1.16.5 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99d2b2a20823b`.

- 1.15.2 `LocalPlayer.java`: `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd`
- 1.16.5 `LocalPlayer.java`: `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b`
- 1.16.5 `AABB.java`: `514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878`
- 1.16.5 `CollisionGetter.java`: `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`
- 1.16.5 `Shapes.java`: `ec218dca68cad3525364967aadfbb8c0bead0cc44aa3ee2beba31e00e55ae2d4`
- 1.16.5 `CollisionSpliterator.java`: `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`
- 1.15.2 `Mth.java`: `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4`
- 1.16.5 `Mth.java`: `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290`
- 1.15.2 `Player.java`: `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`
- 1.16.5 `Player.java`: `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`
- 1.15.2 `LivingEntity.java`: `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`
- 1.16.5 `LivingEntity.java`: `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`

The proof's exact hash list does not currently print the newly inspected `Mth`, `Player` or `LivingEntity` fingerprints, despite its body relying on those sources. They are pinned here in the independent review and were individually matched against their own manifests. No canonical source, product, wiki, backlog, build, test, decompilation or runtime files were changed or used.
