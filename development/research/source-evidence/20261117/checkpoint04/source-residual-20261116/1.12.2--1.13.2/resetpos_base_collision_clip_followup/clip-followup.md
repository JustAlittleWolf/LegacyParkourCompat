# Base collision clipping follow-up (1.12.2 → 1.13.2)

## Evidence identity

Exact source sets: Ornithe Feather Gen2 build 2, `decompiled_minecraft/{1.12.2,1.13.2}/ornithe-feather`; source-manifest SHA-256s `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

Inputs rechecked by SHA-256: base dispatch projection `2199f933da3c36f38bdffe942dac095e66c5948b3af9654adc06bbf1af9fef04`; provider ledger `f5133f3574a8a4bc9c2aae01a357f9a2b4f9507c37b5cd7ebc669c4a129b44b2`. The bounded key-join report/CSV and independent ACCEPT are `fed592c29be830f0ae227c695e14541d4f77becdc3e88a4f81f45fd97f31a815`, `c6bd748d0863ea4bc7f6098d0268d579b8da22216be23086400ffcd45ce608f5`, and (verdict) `has_collision_key_join_audit/verdict.md`; its verdict covers only the stated exact-key base-dispatch filter and `end_portal` exception. Reused Structure Void report is ledger-listed; no provider geometry was re-audited.

Compared consumer file SHA-256s: 1.12.2 `Entity.java` `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; 1.13.2 `Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; 1.13.2 `VoxelShapes.java` `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`; 1.13.2 `WorldView.java` `9577360b1a1bdde9280f0b725e5e6dde45d9a54719cd7ff933f3ecd31b971f`.

## Consumer trace and bounded result

1.12.2 `Entity.move` obtains `List<Box>` from `World.getCollisions` and clips each requested axis against each box in Y → X → Z order (`Entity.java:567–600`); each resulting nonzero offset translates the player shape before the next axis. 1.13.2 queries block/entity shapes once for the requested movement, then calls `VoxelShapes.calculateMaxOffset` in the same Y → X → Z order and translates the player shape by each clipped offset (`Entity.java:578–598`; `VoxelShapes.java:194–205`). Empty collision results leave the requested axis displacement unchanged in both consumers (old loops execute zero times; new shape stream has no elements).

**Bounded no-consequence result:** no eligible false-gate key in the accepted exact-key base-dispatch join can affect this clipping branch. Its 115 common registered keys are all `hasCollision=true`. The identified false-gate common key `minecraft:end_portal` is outside that subset because the old effective `EndPortalBlock.addCollisions` is empty; it appends no old obstacle. The ledger's bounded `minecraft:structure_void` provider packet independently records empty collision on both sides (old override returns empty; new base `hasCollision=false` returns empty). Thus neither identified false-gate route supplies a collision candidate to clip player displacement or change the movement collision-query result through this gate.

This does not erase the separately established consumer-relevant candidate-filter difference (including the composite-stair witness); that concerns which nonempty candidates are admitted, not the new false gate. It does not prove that every legacy key/provider/state/context has been joined to `hasCollision`, or resolve aliases/unmatched keys. The roster/ledger explicitly retains such provider and state/context gaps. Pair coverage and runtime parity remain open.
