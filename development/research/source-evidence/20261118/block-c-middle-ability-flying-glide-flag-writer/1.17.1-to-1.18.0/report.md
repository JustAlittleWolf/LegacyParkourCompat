# Ability-flight glide clear: 1.17.1 → 1.18

## Scope and source identity

Blind source-only comparison of the specified reachable case: a local player with `mayfly` and `abilities.flying` true, airborne and dry, not a passenger, with shared flag 7 already true and an Elytra in the chest slot. `LocalPlayer` permits creative/always-flying or `mayfly` flight toggles in `aiStep` (1.17.1 lines 728–739; 1.18 lines 726–737). Its separate Elytra start request checks chest-slot Elytra and enabled state (1.17.1 lines 747–750; 1.18 lines 745–748); this report takes the requested already-active glide as the precondition and traces its movement.

Both inputs are Mojmap and exact release IDs 1.17.1 and 1.18. Readiness markers say `status: ready`, `mapping: mojmap`; their declared source manifest SHA-256 values match the manifests on disk. 1.17.1 ready marker SHA-256: `c7180dce94d7b57b53a964445b0c788c71f23ba3ba40eea593c11cc939185e9b`; 1.18 ready marker SHA-256: `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f`.

Manifest identities: 1.17.1 `mojmap.sources.sha256` SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; 1.18 `mojmap.sources.sha256` SHA-256 `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78`. Both `ready.json` `sourceManifestSha256` values match these hashes. The 1.18 marker also binds provenance SHA-256 `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5`; 1.17.1 ready marker does not declare a provenance hash. Its sidecar `mojmap.provenance.json` is present (SHA-256 `de197f77473955635e8c5730e113f227af2b8af9306b74e8a54a765eafc5a333`), but is not asserted by that ready marker.

Compared source-file SHA-256 values below were checked against each exact side's source manifest:

| Source | 1.17.1 | 1.18 |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| `net/minecraft/world/entity/LivingEntity.java` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| `net/minecraft/world/entity/player/Player.java` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |

## Trace and comparison

In both releases, `LivingEntity.isFallFlying()` at 1.17.1 line 3013 / 1.18 line 3015 returns shared flag 7. With that flag initially true, the `Player.travel` ability-flight branch is entered when `abilities.flying && !isPassenger()` (1.17.1/1.18 lines 1424–1435). The dry condition skips the swimming prelude. The branch saves the pre-super vertical velocity, sets the temporary flying speed, then calls `super.travel`.

`LivingEntity.travel` enters its `isFallFlying()` branch (1.17.1 lines 2068–2110; 1.18 lines 2070–2112). It applies the gliding velocity calculation and drag, then performs the real player movement via `move(MoverType.SELF, getDeltaMovement())` (1.17.1 line 2097; 1.18 line 2099) while flag 7 is still true. Its separate `setSharedFlag(7, false)` is gated by `onGround && !level.isClientSide` (lines 2109 / 2111), so it does not clear the flag for this airborne local-client case.

On return from the real move, `Player.travel` restores horizontal velocity from the movement result but overwrites vertical velocity with saved pre-super Y times `0.6`, restores the prior flying speed, resets fall distance, and unconditionally calls `setSharedFlag(7, false)` (1.17.1 lines 1430–1433; 1.18 lines 1430–1433). Thus a still-active Elytra glide receives one actual glide-physics move under the selected flight branch, then flag 7 is directly cleared after movement on the client. The flight branch is selected for the next travel call once the flag is clear.

Difference: The post-move flag-7 clear is present in both versions at the same operation position, with the same `abilities.flying && !isPassenger()` gate; no 1.17.1→1.18 behavioral delta is found for this flag-clear behavior in the assigned case. The adjacent fall-distance statement was refactored from `this.fallDistance = 0.0F` (1.17.1 `Player.java:1432`) to `this.resetFallDistance()` (1.18 `Player.java:1432`). In 1.18 `Entity.resetFallDistance()` (`Entity.java:2101–2102`) is exactly `this.fallDistance = 0.0F`, so this adjacent fall-distance edit is semantically separate and has no additional state effect.

## Coverage boundary

Compared the local ability-flight eligibility/toggle context, the Elytra/flag predicate, the `Player.travel` ability-flight branch, the `LivingEntity.travel` gliding branch, and the 1.18 fall-distance reset helper. This is the requested bounded path only; it is not a complete tick/inventory audit or pair freeze.