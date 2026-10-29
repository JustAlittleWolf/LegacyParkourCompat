# Independent audit of the 1.20.3 source-identity correction

## Verdict

The correction file SHA-256 matches the supplied immutable identity: `040d2f04d3fa3129ff12300aa494c31ef48c23bcf15c8a9b2a1c61a9b76d1379`. The current `report.md` SHA-256 is `4a7ba4b23a8e08aa9d530497ccb8582d37ec2dee9081b910ae91484a63cc2851`, matching the correction's stated current report identity. The assignment-time report SHA it references (`b170f86b83a14b07ae7be268ba439dcd1f22bbc6526b2c48d3da10078cd33897`) is preserved as a previously supplied digest; that older byte revision was not available for independent comparison in this bounded follow-up.

The correction's 1.20.3 mapping-root provenance and seven source hashes were independently recomputed and match. The three-version Boat source comparison supports its gate boundary statement: the exact 1.20.2 body is unconditional; 1.20.3 introduces the `CAN_TURN_IN_BOATS` condition; 1.20.4 has the same body. Thus 1.20.3 is the earliest of the checked, identity-bound versions with this gate. This audit makes no finding about packaged Player tag membership.

## Provenance checks

Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.20.3/mojmap`.

- `.decompile-provenance.json` actual SHA-256: `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`, matching the correction.
- The provenance contains the recorded metadata URL ending in `1.20.3.json` and hash `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`.
- It contains the client JAR URL and input hash `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`, and the `client.txt` mapping URL and hash `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`.
- For ClientLevel, LocalPlayer, LivingEntity, Entity, Player, Boat, and EntityTypeTags, the hash stored in the provenance `sources` map equals the recomputed staged file hash and equals the correction's listed value.

This verifies the local provenance record and its consistency with the staged source files. The client and metadata payloads themselves were not available for direct rehash; that limitation does not erase the source identity recorded by the checked mapping-root provenance.

## Method evidence

- 1.20.2 `Boat.positionRider`, source hash `8d01cdf2502e17ffd03f85c4add4b87316baaeea609f40a3d6f66d0a3c110686`, lines 700–709: after `super.positionRider`, yaw/head yaw writes, clamp, and full-boat Animal adjustment execute unconditionally.
- 1.20.3 `Boat.positionRider`, source hash `f0296d2198d1d0d1f65162126197249789c5d22f081841af454af3a074024109`, lines 665–675: after `super.positionRider`, those writes and the Animal adjustment execute only inside `if (!passenger.getType().is(EntityTypeTags.CAN_TURN_IN_BOATS))`.
- 1.20.4 has the same Boat source hash and method body as 1.20.3.

The 1.20.3 local player trace is internally intact: ClientLevel explicitly ticks a `Player` passenger; LocalPlayer delegates through LivingEntity and Entity ride ticks; Entity dispatches to its vehicle's `positionRider`. Player's constructor uses `EntityType.PLAYER`. Each of those source hashes matches both the provenance record and the correction.

## Preserved original audit

The prior independent audit remains unchanged at `audit.md`, SHA-256 `af774bca0603bd792a0e5d64500a11403694762817a96e0cc33003fb267c7f96`. It records the original assignment-report digest mismatch and now-corrected 1.20.3 identity finding. No packaged tag resource was inspected or inferred in this follow-up, as instructed.
