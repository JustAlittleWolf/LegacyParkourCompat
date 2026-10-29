# 1.8.8 → 1.8.9 ordinary client movement boundary

## Scope and verdict

This is a bounded endpoint source comparison for the exact 1.8.8→1.8.9 client ordinary-player-movement boundary. It uses the accepted source-only 1.8.2→1.8.3 full-audit as context (report SHA-256 `c3c6a4c371868eff642ffd585b15dd7f7b6da43ae6255ca524d68f2c776f0bc6`) and does not repeat the 1.8.5/.6/.7/.8 interior classifications.

No changed or added ordinary-movement root, collision/shape provider, or movement dependency was found. Both exact Java trees contain 1,612 paths with identical path sets; each side’s full tree agrees with its canonical source hash marker. There are 26 changed Java-source hashes. The changed roots that intersect the general client/support surface are `Minecraft.java`, `GameOptions.java`, and `MathHelper.java`; their changed hunks are release metadata, an appended Realms-notifications setting, and HSV-to-RGB local renaming, respectively. None changes movement input, tick/travel math, position/velocity/box writes, collision or block response.

This result does not independently freeze the complete 1.8.8→1.8.9 pair: the assignment was a boundary bridge, and full-pair tick-graph coverage/audit remains a master-level gate. No intermediate patch introduction claim or runtime parity claim is made.

## Canonical source identity

| Release | Canonical source root | Identity / mapping | Source marker and verified coverage |
|---|---|---|---|
| 1.8.8 | `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.8/ornithe-feather/` | Ornithe Feather Gen2 build 2; vanilla client SHA-256 `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8`; mapping JAR SHA-256 `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f` | embedded `.decompile-provenance.json` SHA-256 `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1`; embedded 1,612 source hashes match all 1,612 Java files |
| 1.8.9 | `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather/` | Ornithe Feather Gen2 build 2; vanilla client SHA-256 `14f0d96d1a56fb4f5c3b2233d00699525893fe5ce3dcf181e7de59120595d298`; mapping JAR SHA-256 `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da` | `ornithe-feather.sources.sha256` SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; 1,612 entries match all 1,612 Java files; ready marker records version `1.8.9` and the same source-manifest hash; verification marker says `verified-identical-source-and-input-artifacts` |

The 1.8.8 provenance inputs bind the source root to the exact 1.8.8 client URL/version metadata (Mojang metadata payload SHA-256 `7f002c1dca74cc51ce995e7fdb465ab4984c940e77a1b1c4f2b09bed8702e558`; Feather metadata payload SHA-256 `39221eeab4552d7ad873c303a9237ddbb1faad19e7302a80d359fe2b27eaa3f9`) and `feather-gen2-1.8.8+build.2-mergedv2.jar`. The 1.8.9 provenance/ready markers name exact version `1.8.9`, mapping namespace `ornithe-feather`, and the 1.8.9 Feather Gen2 build 2 artifacts; its ready marker records version metadata ID `1.8.9`, artifact-manifest SHA-256 `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`, and its verification marker reports source identity with zero source-file differences. No parent/inherited release field exists in the 1.8.8 embedded marker; its exact release is instead bound by the client and Feather metadata URLs/hashes. The 1.8.8 root uses the embedded provenance format rather than the later outer ready/provenance markers. The marker versions and mapping-root names agree; I verified both trees directly against the hashes they carry.

The matching-path comparison found 1,586 identical source hashes and 26 differing source hashes, with zero added or removed Java paths. Every changed path and both endpoint hashes are in `changed-source-hashes.csv` (CSV SHA-256 supplied separately).

## Movement roots and providers

The following movement roots are path/hash-identical at both endpoints:

| Path | SHA-256 at both endpoints |
|---|---|| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| `net/minecraft/entity/living/LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `b322144fcc965c9bf5f6ff18882d17f6f0e1cdaf0e545b1c7663c662aceb029b` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| `net/minecraft/client/entity/living/player/Input.java` | `aabf9cfed6156121c67c9f16002ccc02f14fa6213d5717732dcf9a1d26368b74` |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `a5e5b2033322f8867cd845e4095cea7a82888f6382cbfafefc559cf9ba3a76dd` |
| `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| `net/minecraft/client/world/ClientWorld.java` | `710abde0f70c471a081abdfb3b70da681c6d817329801f8a2a49af88a25ee02b` |
The identical full inventory also means no `net/minecraft/block/**` collision provider, entity shape provider, packet/client-handler movement dependency, or other movement source was added or changed. The changed-path inventory has no block, entity, world, network-packet or movement-provider path. `GameOptions` is the sole changed movement-adjacent root: the diff only adds a Realms-notifications flag/option and corresponding UI serialization; the movement key-binding declarations and input conversion remain unchanged. `MathHelper`’s only hunk is local-name alpha-renaming in HSV-to-RGB; its float multiplication, integer casts, clamping and bit shifts are identical. `Minecraft` only changes display and HTTP version strings.

`EffectCommand.java` was checked because it can create status effects that later feed a player’s movement. Its changed locals correspond by role: seconds input (default 30) remains the positive gate; duration ticks remain parsed seconds or seconds × 20 (including instant-effect duration); amplifier remains default 0 / parsed 0..255; the `StatusEffectInstance` and success-message argument positions retain the same values. No effect duration, amplifier, or player-movement input delta was found there.

## All 26 changed Java paths

See `changed-source-hashes.csv` for exact endpoint hashes. Diff hunks were inspected for all 26 rows. Dispositions:

- Release strings/metadata: `DebugOverlay`, `Minecraft`, `ServerListEntry`, `RealmsSharedConstants`, `IntegratedServer`, `MinecraftServer`, `RemoteHandshakeNetworkHandler`, `Snooper`, and `CrashReport`.
- Realms-notification UI/option: `OptionsScreen`, `TitleScreen`, `Screen`, `GameOptions`, `Realms`, `RealmsBridge`, `RealmsScreen`, and `RealmsScreenProxy`.
- Rendering/UI only: `ChestRenderer` and `WrittenBookItem`.
- Source-name/generic-only changes: `SoundManager`, `BanEntry`, `EnchantCommand`, and `CommandResults`.
- Command-side local renames: `EffectCommand` and `SummonCommand`.
- HSV color local renames: `MathHelper`.

No hunk modifies ordinary client movement.

## Coverage limits

Endpoint Java-source facts only. The accepted 1.8.2→1.8.3 audit is used as frozen cross-check context, not as a claim that every method/path in 1.8.8→1.8.9 was independently retraced. No decompile, test, launch, runtime comparison, or Git mutation was performed.
