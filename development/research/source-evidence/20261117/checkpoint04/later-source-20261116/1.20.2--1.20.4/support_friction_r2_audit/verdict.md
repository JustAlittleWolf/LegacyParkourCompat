# Independent blind source audit: support friction r2

Verdict is bound only to candidate `build/later-source-20261116/1.20.2--1.20.4/support_friction_revision_r2/coverage-r2.md`, SHA-256 `f23763905b04e0b6bae0e13d68b7bd34b66484a065b0d4e9cd380cd3a92f9e14`.

## Verdict

**Pass for the bounded S5.3 source claims and stated partial disposition.** I independently rechecked endpoint/source identities, the listed S5.3 source hashes, relevant method bodies, provider setter occurrences, and the player speed-factor tick path. I found no material misstatement in the report and no endpoint difference in the examined S5.3 selector, factor, friction-consumer, or provider bodies.

## Endpoint identity and hashes

Both canonical roots are ready Mojmap/Vineflower Java source snapshots for the named versions, using Java 25. Their ready markers, provenance files, and manifests are present. The source-manifest file hashes agree with the ready markers and candidate report: 1.20.2 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; 1.20.4 `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`. The artifact-manifest hashes also match the markers and report: 1.20.2 `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54`; 1.20.4 `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948`. The artifact manifests list the reported client-jar and Mojmap mapping-input digests, and both provenance records identify the same 1.20.1/1.20.2/1.20.4/1.20.6 Mojmap Vineflower batch.

Recomputed SHA-256 values match the report for every listed S5.3 Java source file:

| Source | 1.20.2 | 1.20.4 |
|---|---|---|
| `Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `Block.java` | `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656` | `9a777d44c9b6c55677132fb5a56d46c9aa322e42faf956749ef80443817416c4` |
| `BlockBehaviour.java` | `a6d88726ce4642fc0a61f46a01347bb0b74887b61ef55af7e7c76723789a0b57` | `6284a7d08a6d0d475cd4fe8aea62b35153ae8fbc0bce5f51788389f679233b54` |
| `Blocks.java` | `75e8e77adab4a07b02ef95619be83a004f5f79810cf3f07157a713d714881253` | `5392c4349317e55c2989865be38c17b94e6e0543e7017e6a2a64d79217e83643` |
| `BlockTags.java` | `c40cebc2b59225e4cba18f057d519fbd00abed513091ef98610c0a9a0d993d22` | same |
| `EnchantmentHelper.java` | `c98b9e538aae7fea33a125560400f346745bac993e859ffd3912da58f3cc1128` | same |

Each listed source is present in its endpoint source manifest with the corresponding digest. The decompiled roots contain only Java files (4,904 and 5,048 respectively), consistent with the report's statement that resource/tag JSON membership was unavailable there.

## S5.3 claim checks

- **Support selector:** `Entity.getBlockPosBelowThatAffectsMyMovement()` passes `0.500001F` to `getOnPos(float)` in both endpoints. With a recorded support position, the conditional preserves it for walls and `FenceGateBlock` regardless of that offset. The fence exception only preserves the support position when offset `<= 0.5`; `0.500001F` makes that clause false, so fences use `support.atY(floor(position.y - offset))`. With no recorded support position, the method floors X/Z and the offset Y. This validates the report's distinction between fences and walls/gates. The production of `mainSupportingBlockPos` was not checked and remains open.
- **Jump factor:** The entity selector reads in-entity and support-block jump factors and falls back to the support factor only when the in-entity factor equals `1.0`; `LivingEntity.getJumpPower` applies the selected factor to `0.42F` before jump-boost addition. The compared relevant bodies agree.
- **Speed factor:** `Entity.getBlockSpeedFactor()` uses the in-entity block factor, returns it directly for water/bubble columns, and otherwise falls back to the support factor only for exact `1.0`. `LivingEntity` returns `1.0F` for a Soul Speed-enchanted player on a `SOUL_SPEED_BLOCKS` support, else delegates. `Player` returns `1.0F` during flying or fall-flying, else delegates. These relevant bodies agree.
- **Player reachability:** In both endpoints, `Player.tick()` calls `super.tick()`; `LivingEntity.tick()` calls `super.tick()`; `Entity.tick()` calls `this.baseTick()`; `LivingEntity.baseTick()` calls `super.baseTick()`; and `Entity.baseTick()` invokes virtual `this.getBlockSpeedFactor()` before multiplying horizontal delta movement by the result. Thus the speed-factor selection is reached through the player tick path, with runtime dispatch through Player/LivingEntity/Entity overrides as described. No claim about the excluded support-position writer follows from this trace.
- **Friction consumer:** The ordinary `LivingEntity.travel` branch reads the selected support state’s friction, computes on-ground `friction * 0.91F` (or `0.91F` airborne), and passes friction to `handleRelativeFrictionAndCalculateMovement`. Player travel delegates to `super.travel` outside its distinct branches. Relevant bodies/order match in the endpoints.
- **Providers:** Defaults remain `0.6F` friction, `1.0F` speed factor, and `1.0F` jump factor. Enumerated setter calls in both endpoint `world/level/block` trees match: friction `0.98F` (ice/frosted ice/packed ice), `0.989F` (blue ice), `0.8F` (slime); speed factor `0.4F` (soul sand and honey); jump factor `0.5F` (honey). No provider-value delta was found.
- **Open dependencies:** `BlockTags.SOUL_SPEED_BLOCKS` has the same Java tag key, but its effective resource membership is not established. The tag resources and `mainSupportingBlockPos` production remain explicit open gates. The report does not claim either dependency closed or assert a full-pair verdict.

This verdict accepts only the claims checked above for the immutable r2 candidate. It does not establish complete pair coverage, freeze, implementation acceptance, runtime parity, or an intermediate patch boundary.
