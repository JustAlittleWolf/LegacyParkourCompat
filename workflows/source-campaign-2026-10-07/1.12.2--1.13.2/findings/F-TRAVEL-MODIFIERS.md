# F-TRAVEL-MODIFIERS — 1.13.2 changes travel branches and constants

- Status: candidate family; split branch records and modifier source closure pending
- A source: `net/minecraft/entity/living/LivingEntity.java`, moveRelative/travel body lines 1425-1618; SHA-256 `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`.
- B source: same class, body lines 1478-1688; SHA-256 `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`.
- Source-confirmed candidates: Slow Falling selects gravity factor 0.01 when velocityY <= 0 and clears fallDistance; Elytra acceleration expression changes and adds a horizontal-look guard; water travel adds sprint/Dolphin's Grace handling and different gravity/clamp logic; ground/air coefficient changes from 0.16277136F to 0.16277137F; lava gravity uses factor/4.0.
- Reachability/dependencies: each branch is player-reachable but conditional on effects/equipment/fluids/onGround. Must split into one finding per branch and trace effect/attribute/enchantment registrations and source-state writers before freeze.
- Limits: no consolidated historical rule is inferred from default-valued expressions; preserve the source operation order and exact float literals in follow-up records.
