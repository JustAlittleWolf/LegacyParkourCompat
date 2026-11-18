# Independent source audit verdict: 1.9 → 1.9.1 ability-flight / fall-flying overlap

## Verdict

**ACCEPT the bounded no-difference claim, with the ownership/synchronization qualification below.** The immutable reviewed report is `c-early-19-191-ability-flying-glide-flag-writer/report.md`, SHA-256 `e178f379ac7f92bd8365ba554988636d2464c5e2c9b57148864be6e74387ed45`.

The bounded claim holds for an ordinary local player with `canFly == true` locally and flag 7 already true when `PlayerEntity.moveRelative` enters its ability-flight branch. Local double-tap jump toggles `abilities.flying` without testing `isFallFlying`; the glide flag therefore need not be synthetic, concurrently written or latency-raced. `LivingEntity.tick` invokes `mobTick` before the later travel movement path. With ability flight true and not riding, `PlayerEntity.moveRelative` calls `super.moveRelative` first. `LivingEntity.moveRelative` tests flag 7 there and, when true, runs the fall-flying calculations and `move(...)`. Only after that call returns does the player wrapper restore saved vertical velocity times `0.6`, restore air speed, set `fallDistance = 0.0F`, and clear flag 7. The glide branch's possible `fallDistance = 1.0F` assignment (`velocityY > -0.5`) precedes its `move`, and is a distinct field write from the later flag clear.

**Ownership qualification:** the player wrapper's `setFlag(7, false)` is an unconditional direct write only inside `abilities.flying && !isRiding()`. On a local client player it writes that client's tracked-data value after this movement call; it is not the server's authoritative clear. The server sets flag 7 on an accepted `START_FALL_FLYING` action, and its own grounded glide clear is guarded by `!world.isClient`. The ordinary `syncAbilities()` call sends a separate ability packet; it neither waits for an acknowledgement nor carries flag 7. The server gates the requested flying ability with its own `canFly`. Thus this audit confirms the local same-call order and does not infer that the client clear immediately clears the server's tracked flag.

The reviewed release comparison is unchanged in the relevant branches in both exact Feather trees. The 1.9.1 Elytra sound after the start-action send is outside the movement claim. No A/B, build, test or launch was needed or performed.

## Evidence checked

Source-relative paths below are under `decompiled_minecraft/<release>/ornithe-feather/`.

- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: 1.9 `686–720`, `750–754`; 1.9.1 `687–722`, `752–756`. The ordinary non-spectator toggle requires `canFly`, `!bl`, jump input and a nonzero double-tap timer; it has no fall-flying exclusion. `syncAbilities()` is called on the toggle. The elytra start-action branch is separate and explicitly excludes both existing fall-flying and ability flight. 1.9.1 adds the Elytra sound after sending that action.
- `net/minecraft/entity/living/LivingEntity.java`: 1.9 `1289–1341`, `1538`, `1693–1699`, `2038–2040`; 1.9.1 `1293–1345`, `1542`, `1697–1703`, `2042–2044`. The glide branch reads `isFallFlying()` / flag 7, may write fall distance before movement, then executes its motion math and `move`; its grounded clear is server-only. The corresponding relevant branch is otherwise unchanged.
- `net/minecraft/entity/living/player/PlayerEntity.java`: both versions `1365–1384`. Ability-flight branch calls `super.moveRelative` before its post-call writes; the local direct flag-7 clear follows the distinct fall-distance reset.
- `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java`: both `846–855`, `1082–1085`. The server validates the start action and accepts the requested ability-flight bit only with authoritative `canFly`.
- `net/minecraft/server/entity/living/player/ServerPlayerEntity.java`: both `1040–1047`; accepted glide start sets server flag 7. `LivingEntity.isFallFlying()` reads flag 7 as above.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: both `296–299`; `syncAbilities()` sends `PlayerAbilitiesC2SPacket`. `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`: both `1319–1328`; server ability sync updates client ability fields. These are separate from the flag-7 write.

## Immutable source identity and integrity

`buildSrc/README.md` defines the provenance manifest format. I checked actual manifest SHA-256s and the report's actual cited source-file SHA-256s against each manifest's `sources` digest dictionary. All listed file digests match.

| Release / mapping tree | Actual manifest SHA-256 | Pinned Feather metadata / archive SHA-256 | Compared client-side source SHA-256 (`LocalClientPlayerEntity`, `PlayerEntity`, `LivingEntity`) |
|---|---|---|---|
| 1.9 / Ornithe Feather | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | metadata `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371`; archive `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06`; `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1`; `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` |
| 1.9.1 / Ornithe Feather | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | metadata `da56c9e7efe5c929b2ec8d99a8e2bbc6825847f2852a0e717fcd6c674db73dc4`; archive `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb`; `d7035f08f03b01823dff70ca504f7244f9b0753542cc0f6dd7301e66f8866b71`; `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |

The remaining cited authority/sync file hashes also match both manifests: `ServerPlayNetworkHandler.java` `89609533320e02ed781c4aa87caf9eb55417756bba8a6b0ea53bb5f2e1836634`; `ServerPlayerEntity.java` `dc6b4d3dfff336aed5efa03c9f97a3ae09531e0ae8cd80f8cb6de949333fbe10`; `ClientPlayNetworkHandler.java` `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6`; `PlayerAbilitiesC2SPacket.java` `e0bab6badb7937127365c8834efaa92f61034fae88717f95972e13bfb90bdad1`; and `PlayerAbilitiesS2CPacket.java` `808d7f80bfc07684566e6a1dcf53b14a68677de599344f9db50bd6af84a85588`.

## Audit input and output identity

- Input report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-early-19-191-ability-flying-glide-flag-writer/report.md`
- Input SHA-256: `e178f379ac7f92bd8365ba554988636d2464c5e2c9b57148864be6e74387ed45`
- Exact read-only sources: `decompiled_minecraft/1.9/ornithe-feather/` and `decompiled_minecraft/1.9.1/ornithe-feather/`
- Output: `c-early-19-191-ability-flying-glide-flag-writer-audit/verdict.md`
