# Independent source audit: fluid-current response threshold

**Verdict: ACCEPT for the bounded pre-travel fluid-current route only.**

Candidate: `build/later-source-20261116/1.21.11--26.1.2/fluid_travel/coverage.md`  
Candidate SHA-256: `9054D73FB7CACBD050A2D6F7C8B774E54BB476D845E24F0A61D1AD7CFBE238A0`

## Source identity and integrity

- `decompiled_minecraft/1.21.11/mojmap.ready.json` SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`, source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`, provenance SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; exact run declares 1.21.11 Mojmap.
- `decompiled_minecraft/26.1.2/unobfuscated.ready.json` SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`, source manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`, provenance SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; exact run declares 26.1.2 unobfuscated.
- Both ready markers' artifact-manifest digests matched the local manifests (`c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` and `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`).
- Independently rehashed every cited Java file and checked it against the corresponding ready-marker-bound source manifest: all 13 entries match, including `Entity.java`, `LivingEntity.java`, `Player.java`, `LocalPlayer.java`, `FlowingFluid.java`, `AABB.java`, and the 26.1.2 `EntityFluidInteraction.java`.

## Claim and witness

The bounded source comparison is supported. In 1.21.11, `Entity.updateFluidHeightAndDoFluidPushing` accepts every nonzero accumulated current (`length() > 0.0`), then averages the vector for a `Player`, scales it, applies the existing minimum-impulse rule, and writes the result. In 26.1.2, `EntityFluidInteraction.Tracker.applyCurrentTo` suppresses the accumulated vector when its squared length is `< 1.0E-5F` before averaging/scaling/minimum-impulse handling. The associated height origins also differ: the old value subtracts the deflated box minimum; the new value subtracts the entity bounding-box minimum while retaining the deflated box for overlap.

The stated numeric witness checks out conditionally. With a single matching fluid cell whose top is `1.0`, entity bounding-box `minY = 0.997`, a unit horizontal flow, and a fully loaded world, the old deflated minimum is `0.998`, so the old accumulated vector has length `0.002`; the new tracker height/vector length is `0.003`, with squared length `9.0E-6`, below the tracker threshold. The old water scale makes a `0.000028` impulse, which reaches the existing `0.0045` minimum at rest. The new tracker returns before that rule and adds no current impulse. `FlowingFluid.getFlow` normalizes a nonzero horizontal gradient at both endpoints, and the source `AABB.deflate` implementations move the minimum inward by `0.001` at both endpoints.

The player route is supported: `LocalPlayer.tick` calls the inherited player/entity tick chain; `LivingEntity.tick` calls `Entity.tick`, which reaches `Entity.baseTick` before `LivingEntity.aiStep` travel. Both endpoint `Player.isPushedByFluid` implementations return `!abilities.flying`. For the stated non-flying local player, the base-tick fluid update applies the current before the later travel simulation. The newer finite-checked `addDeltaMovement` delegates to `setDeltaMovement(getDeltaMovement().add(momentum))`; the old helper uses the equivalent add-then-set sequence, and the witness vectors are finite.

## Boundaries and remaining gates

- This accepts the conditional source behavior and the bounded base-tick witness. The Java roots do not provide `FluidTags.WATER`/`LAVA` resource membership, so the concrete vanilla fluid-tag assignment and applicable tag overrides remain unverified; the witness is conditional on a fluid matching the water tag.
- The exact introduction release is unknown within 1.21.11→26.1.2; only the endpoints were compared. No implementation cutoff is established.
- `LivingEntity.checkFallDamage` is another caller of the fluid-update helper when `!isInWater`; its post-move invocation is outside this pre-travel witness. Multiple-fluid aggregation/cancellation, unloaded-chunk boundaries, vehicle bounds, other water/lava cases, full-pair coverage, and runtime parity remain open.

This verdict accepts one bounded source slice. It does not freeze the pair or establish implementation readiness or runtime parity.
