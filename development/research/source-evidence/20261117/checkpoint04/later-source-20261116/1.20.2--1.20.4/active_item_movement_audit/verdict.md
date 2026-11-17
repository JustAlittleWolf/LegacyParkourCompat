# Independent source audit: active-item movement slowdown

**Verdict: ACCEPT**  
**Audited report SHA-256:** `d101014b3217a3ca4591abba2b6d3266c9a16759c9552161ad3f39787731f404`

## Exact input identity

I independently verified both endpoint identities against their exact release-root sidecars. 1.20.2 is `versionId=1.20.2`, `mapping=mojmap`, `status=ready`; 1.20.4 is `versionId=1.20.4`, `mapping=mojmap`, `status=ready`. Each ready marker's declared source and artifact manifest digests match the corresponding sidecar bytes. Provenance reports the matching release ID and `namespace=mojmap`.

| Version | Ready marker | Provenance | Source manifest | Artifact manifest |
|---|---|---|---|---|
| 1.20.2 | `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f` | `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` | `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` |
| 1.20.4 | `6225b97979420cc743c8b7156a0ce512ef27d0dbf48bfffbc40d3e1c8cb044d1` | `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` | `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1` | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` |

All six cited Java files' actual SHA-256 values match their source-manifest entries and the audited report: `LocalPlayer.java` is `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` at both endpoints; `Player.java` is `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` then `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd`; `LivingEntity.java` is `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` then `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d`.

## Audit result

The LocalPlayer source is byte-identical across endpoints. The reported gate is present in `LocalPlayer.aiStep()` after input ticking and the tutorial input callback: `isUsingItem() && !isPassenger()` scales `input.leftImpulse` and `input.forwardImpulse` by `0.2F` and clears `sprintTriggerTime`. The local active-use override accepts a nonempty stack only when not already using, calls the superclass, then sets `startedUsingItem` and the hand; `isUsingItem()` reads that local flag; stop calls the superclass and clears it. The synchronized living-entity flag callback starts or stops local use from the use/off-hand bits, as described.

The paired `LivingEntity.tick`, `updatingUsingItem`, `startUsingItem`, `stopUsingItem`, `isUsingItem`, and `aiStep` method bodies are equal between endpoints. In both, tick refreshes active use before the virtual AI step; a mismatched held item calls virtual stop; the client slowdown is therefore driven by the same active-use state lifecycle. `LocalPlayer.serverAiStep()` copies the already-scaled impulses into `xxa`/`zza` when the player is the controlled camera. `LivingEntity.aiStep()` constructs the movement vector from `xxa`, `yya`, and `zza`, then invokes virtual `travel` outside the ridden-entity path. For an ordinary LocalPlayer, `Player.travel` delegates to `super.travel` in its non-flying branch; its flying branch also delegates to `super.travel`. This verifies the stated handoff without establishing downstream travel parity.

`Player.travel` is not byte-identical: its 1.20.2 body calls `checkMovementStatistics(...)` after travel, while the 1.20.4 body omits that call. This is post-travel statistics accounting and does not alter the audited active-use gate, impulse values, or dispatch into `LivingEntity.travel`; the report explicitly excludes other travel behavior and does not claim full travel equivalence. I found no in-scope discrepancy or unsupported claim in the bounded report.

## Acceptance boundary

ACCEPT applies only to the immutable report above and its stated active-item slowdown, active-use lifecycle, and input-to-travel handoff claim. It does not close pair coverage, other travel operations, runtime parity, or the unrelated `Player.travel` statistics difference.
