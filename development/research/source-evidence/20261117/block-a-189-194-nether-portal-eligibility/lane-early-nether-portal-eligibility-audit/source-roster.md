# Frozen source roster — early Nether portal eligibility audit

Assignment: exact 1.8.9 → 1.9.4, Ornithe Feather; candidate operation is Nether portal body contact / player eligibility / local cooldown / server gate.
Host and shell: Windows PowerShell.
Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` (read-only).

Both source trees exist at the exact release and mapping paths. Neither root contains `.decompile-provenance.json`; per `buildSrc/README.md`, these are legacy source trees with unverified provenance. The file digests below bind this audit to the bytes inspected but do not elevate the source preparation provenance.

## Source set and SHA-256

### 1.8.9 / Ornithe Feather
- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/block/PortalBlock.java` — `bb9d2cba26df71c8383408fcccc93b849eebecd3d36924a63ee80cba8734867e`
- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/Entity.java` — `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`
- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` — `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88`
- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/ClientPlayerEntity.java` — `b322144fcc965c9bf5f6ff18882d17f6f0e1cdaf0e545b1c7663c662aceb029b`
- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` — `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053`
- `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/server/entity/living/player/ServerPlayerEntity.java` — `047158bda6fa8fe2ea8a097972c098c5abe492ffbbe280006ae7592cd97fbb81`

### 1.9.4 / Ornithe Feather
- `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/block/PortalBlock.java` — `8f6c4502a2b910ad8eaaefed19d162a3319e9afc6802c8ccb3e7e05b3987c598`
- `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/entity/Entity.java` — `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`
- `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java` — `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`
- `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/ClientPlayerEntity.java` — `68f6e640808d8b31c1a99d1470075606a42290600fd45435aa86c15eb8db5ef0`
- `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` — `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`
- `decompiled_minecraft/1.9.4/ornithe-feather/net/minecraft/server/entity/living/player/ServerPlayerEntity.java` — `8e65663709c6a41f845ddb703b9ad96ecf05a67cc17bfadcda98328900cd67cd`

Coverage selected before candidate comparison access: block contact callback; entity tick/collision caller and portal bookkeeping; inherited player/vehicle/rider eligibility; local client portal contact/cooldown behavior; server player transfer eligibility, max-time threshold, and time increment/reset order. End portal transfer geometry is out of scope.