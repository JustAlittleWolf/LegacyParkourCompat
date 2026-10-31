# `AbstractClientPlayer` changed-member route audit

**Pair:** Minecraft 1.15.2 Mojmap → 1.16.5 Mojmap. **Disposition:** all changed members in this class are closed for fixed-profile LocalPlayer tick/travel; no remaining movement dependency was found in this class. This is a member-level disposition, not a blanket verdict for the pair or its other classes.

## Exact source identity

The pair identity and direct-manifest verification are from `.native-source/20261030/1152-1165-pool33/input-travel-audit/audit.md` and `source-hashes.json`:

| Side | Source root | Canonical `mojmap.sources.sha256` SHA-256 |
|---|---|---|
| 1.15.2 | `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16.5 | `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` |

For `net/minecraft/client/player/AbstractClientPlayer.java`, older SHA-256 is `db13c55b0ba08cb2a40cf2da7a3954de24e1e1f55a197f125cc1aa6ed109be0b`; newer SHA-256 is `05c36f559ada7061a4a2ee8efaf05a21502945af83a893cb4c849749afb84859`. Both match the canonical and corrected direct-manifest entries in `source-hashes.json`. The exact source diff contains two changed members: the constructor and `getFieldOfViewModifier`.

## Changed members

| Member | Source difference | Local-player route and disposition |
|---|---|---|
| `AbstractClientPlayer(ClientLevel, GameProfile)` | 1.15.2 calls `Player(ClientLevel, GameProfile)`; 1.16.5 calls `Player(ClientLevel, getSharedSpawnPos(), getSharedSpawnAngle(), GameProfile)`. The old `Player` constructor itself fetched `level.getSharedSpawnPos()`, positioned at block center plus one Y, and used yaw `0.0F`. The new `Player` constructor uses the supplied same shared-spawn position and supplied yaw, with the same position arithmetic. | `LocalPlayer` is constructed through `MultiPlayerGameMode.createPlayer` at login or respawn in both endpoints. Both `ClientPacketListener` handlers set `minecraft.player.yRot = -180.0F` after construction (1.15.2 lines 359–360 and 1073–1083; 1.16.5 lines 381–382 and 1087–1105). Thus the new default-spawn-angle yaw is overwritten before ordinary LocalPlayer tick/travel. The `moveTo` constructor path writes current yaw but leaves `yRotO` at its same default in both cases. The shared-spawn block position is the same input the old `Player` constructor queried. No constructor-only position or rotation difference remains on the LocalPlayer tick/travel route. |
| `getFieldOfViewModifier()` | New code obtains the movement-speed attribute through `getAttributeValue(Attributes.MOVEMENT_SPEED)` (versus `getAttribute(...).getValue()`), then returns `Mth.lerp(options.fovEffectScale, 1.0F, f)`. | The only callers in either source tree are `GameRenderer` (1.15.2 line 296; 1.16.5 line 300), where the result sets camera FOV. The changed interpolation is rendering-only. The method reads movement speed and flying state to calculate FOV, but writes no player movement state and is not called by LocalPlayer tick/travel. Excluded from movement. |

The remaining class members (`isSpectator`, `isCreative`, player-info/skin/cape/Elytra texture lookups and registration, and model-name lookup) are unchanged in the exact diff. They concern game mode presentation or client appearance and do not supply LocalPlayer movement state.

## Adjacent movement candidates kept separate

- The existing Depth Strider and Dolphin's Grace contribution is already closed by `.native-source/20261030/1152-1165-pool33/depth-strider-movement-audit/coverage.md`: with equal water-branch entry and equal state, level lookup, cap/airborne scaling, interpolation, effect check and operation order match. This class audit does not reopen that route or claim the entire water branch is identical.
- Soul Speed is a feature introduced after 1.15.2. Its newer block-speed behavior does not arise from a changed `AbstractClientPlayer` member and is not part of fixed 1.15.2 profile movement. Keep that feature out of this class disposition.

## Conclusion

The class's only rendering change is the field-of-view effect-scale interpolation. Its constructor's spawn-angle change is neutralized by both LocalPlayer construction callers before tick/travel, and the spawn-position source is unchanged. No changed `AbstractClientPlayer` member requires a LocalPlayer tick/travel follow-up. This closes only this class's changed-member route; it does not close changed LocalPlayer, player/core, fluid, collision, provider, packet or other pair paths.
