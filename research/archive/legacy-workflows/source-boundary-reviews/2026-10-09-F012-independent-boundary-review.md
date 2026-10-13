# Independent source-only review: F-012 release boundary

**Review date:** 2026-10-09  
**Disposition:** **ACCEPT**, bounded to the exact four sampled releases and the coefficient/consumer claim.  
**Candidate memo:** commit `1c4317af04f70a596642369a402ce38ccb5492da`, path `workflows/source-boundary-reviews/F012-sprint-air-control-boundary-evidence-2026-10-09.md`, blob `ebd4cb218453a9b2ce7afbe15932d2668dc6b742`.  
**Immutable finding:** commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, `F-012-sprint-air-control-float.md`, blob `947ca63e44ec3f8ecaa710f4168c95bf66e5402a`.  
**Prior independent review:** commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, `2026-10-08-mc1171-1182-snapshot-review.md`, blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`.

## Exact source publication bindings

I checked the ready markers, source/artifact manifests, and direct `Player.java` / `LivingEntity.java` hashes against the canonical exact Mojmap roots identified in the memo. All ready markers report `ready`; their manifest fields match the direct manifest hashes. Relevant identities:

| Release | Ready marker SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | `Player.java` SHA-256 | `LivingEntity.java` SHA-256 |
|---|---|---|---|---|---|
| 1.17.1 | `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| 1.18 | `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| 1.18.1 | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| 1.18.2 | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |

The source manifest rows agree with the direct class hashes. The 1.18 and 1.18.1 Player and LivingEntity files are byte-identical for these methods.

## Coefficient math and sampled boundary

In 1.17.1, 1.18, and 1.18.1, `Player.aiStep` calls `super.aiStep()`, sets `flyingSpeed = 0.02F`, then while sprinting assigns `(float)(flyingSpeed + 0.005999999865889549)`. The literal is a double: the float base is promoted, the double addition occurs, and the result is cast back to float. The three sampled source versions agree on that expression and ordering.

In 1.18.2, the same writer order and base reset are present, but sprinting uses `flyingSpeed += 0.006F`. The compound assignment adds the float operand and stores a float result. Evaluating the two stated expressions from the exact `0.02F` base gives:

- Old expression: `0.025999998673796654`, bits `0x3cd4fdf3`.
- New expression: `0.026000000536441803`, bits `0x3cd4fdf4`.

This is the claimed one-ULP coefficient change. It first appears among the four examined releases at **1.18.2**. The source evidence does not identify the onset in unexamined releases between these samples or establish fidelity for other eras.

## Consumer and delayed tick dependency

The source call order supports the next-tick consumer. `LivingEntity.tick` invokes virtual `aiStep`; Player's override calls `super.aiStep()` before its coefficient assignment. The LivingEntity `aiStep` body reaches `travel` before returning to the Player writer. Therefore the Player writer follows that tick's movement, and the stored coefficient can feed the next tick's movement before the next Player writer.

For the claimed movement consequence, the consumer gates are: the Player remains on the ordinary `Player.travel` path (not abilities flight), is airborne so `getFrictionInfluencedSpeed` returns `flyingSpeed` rather than ground speed, is in non-fluid ordinary travel (not water/lava or fall-flying), and has nonzero relative movement input. `handleRelativeFrictionAndCalculateMovement` passes that speed to `moveRelative`; the airborne `getFrictionInfluencedSpeed` branch returns `flyingSpeed`. These conditions make the adjacent stored coefficients available to the consumer and allow them to change the requested movement vector. They do not establish a particular collision-resolved displacement.

The Player flying-abilities branch temporarily substitutes its own speed and restores the saved `flyingSpeed`, so it is not part of this witness. Fall-flying and fluid branches also do not establish the ordinary-air-control consumer. The memo's ordinary airborne, non-fluid applicability bound is consistent with these source gates.

## Verdict and limits

**ACCEPT** the memo's bounded source claim: the old double-literal expression remains in 1.17.1, 1.18, and 1.18.1; the 1.18.2 source uses float compound addition; the coefficient rounds one ULP higher and is consumed on the next eligible ordinary airborne movement tick. This is a coefficient and consumer finding, not a trajectory claim, full movement-parity claim, or implementation decision.

No sources outside the four listed releases were used to assert historical behavior. No build, tests, game client, server, TAS, Docker, runtime validation, implementation material, or reconciliation/mod content was inspected.
