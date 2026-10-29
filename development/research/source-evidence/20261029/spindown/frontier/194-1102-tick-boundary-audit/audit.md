# Independent source audit: 1.9.4 → 1.10.2 tick boundary

## Scope and result

Audited the immutable author report at `.native-source/20261028/frontier-c/194-1102-tick-boundary/report.md`. Its SHA-256 matches the supplied identity: `4732d3b10870be19df84adb7b360ff208a9bad69465d556f3e1392ea717a6708` (case-insensitive comparison).

Read-only canonical mapped-source roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.9.4\ornithe-feather\`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.10.2\ornithe-feather\`

This audit confirms the report's source identities and the bounded auto-jump/no-gravity observations below. It does not close pair coverage, dependencies, acceptance, implementation, or runtime parity.

## Release-parent evidence identity

| Field | 1.9.4 | 1.10.2 |
|---|---|---|
| Ready `versionId` / `versionMetadataId` | `1.9.4` / `1.9.4` | `1.10.2` / `1.10.2` |
| Ready mapping namespace | `ornithe-feather` | `ornithe-feather` |
| Mapping artifacts in ready/provenance | `feather-gen2-1.9.4+build.2-mergedv2.jar`, `feather-gen2-1.9.4+build.2.tiny` | `feather-gen2-1.10.2+build.2-mergedv2.jar`, `feather-gen2-1.10.2+build.2.tiny` |
| Client input SHA-256 | `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908` | `7cdf7fcdc1c92584a233bf3c42bd7f0df1bdad3007d306831fe50410692be1e9` |
| Source manifest SHA-256 (ready claim = independently hashed file) | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| Artifact manifest SHA-256 (ready claim = independently hashed file) | `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77` | `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116` |
| Provenance identity | `versionId=1.9.4`, `namespace=ornithe-feather`, mapping option `feather`, exact run includes 1.9.4 | `versionId=1.10.2`, `namespace=ornithe-feather`, mapping option `feather`, exact run includes 1.10.2 |

`artifacts.sha256` binds the mapping JAR and Tiny map to `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` / `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027` for 1.9.4, and `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0` / `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1` for 1.10.2. Manifest entry paths use the `yarn/` artifact-cache prefix; the release-parent namespace and provenance identify the mapped output as `ornithe-feather`. The two embedded mapped source roots exist, and their files match the ready-bound source manifests for every selected source below.

The release-parent verification records report respectively 1,819/1,845 source files verified, zero source differences, and zero missing published source files. Each records one derived remapped-JAR difference, explains it as regenerated intermediate output, and records no raw input artifact differences. I did not independently hash the original mapping artifact blobs: the reported campaign artifact-cache path did not contain those files in this checkout. The artifact identity check here is against the release-parent artifact manifests and provenance records.

## Selected source hashes

For each row, the first hash is the current SHA-256 of the file under that release's `ornithe-feather` source root; it also exactly matches that relative path's entry in the corresponding source manifest.

| Relative source | 1.9.4 SHA-256 | 1.10.2 SHA-256 |
|---|---|---|
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `68f6e640808d8b31c1a99d1470075606a42290600fd45435aa86c15eb8db5ef0` | `d9f0ec60a318ac51fd49198aaccd5c3e2b3af95cd3e2df577bb25226f3e0e4e4` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |
| `net/minecraft/client/entity/living/player/Input.java` | `206dea2ff5977599596c907c54f36300c4e72d07d6778d82e8850f57bb0fb009` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` |
| `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302` |
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |

## Independent source retrace

### Auto-jump

- In 1.9.4, `LocalClientPlayerEntity.mobTick` is at lines 645–755. The class has no `autoJump`, `ticksToNextAutojump`, or local `move` override. The ordinary key path samples `input.jumping`, then calls `input.tick()` and proceeds through the existing sprint, flight, mount, and superclass paths.
- In 1.10.2, `LocalClientPlayerEntity.mobTick` is at lines 661–783. A positive `ticksToNextAutojump` is decremented and sets `bl4=true` and `input.jumping=true` (673–677) before `super.mobTick()`. The superclass `LivingEntity.mobTick` calls the locally controlled entity's `serverTickAi` before its jump stage (1.10.2 `LivingEntity.java` 1714–1739); `LocalClientPlayerEntity.serverTickAi` copies `input.jumping` to the movement field `jumping` (602–610). Thus a timer queued as 1 becomes a synthetic input press in the next local mob tick and reaches the ordinary jump stage in that same tick, subject to ordinary jump conditions such as `onGround` and `jumpingCooldown == 0`.
- The new local `move` override calls `super.move(dx,dy,dz)` first, then passes the actual resulting horizontal displacement to `autoJump` (1.10.2 `LocalClientPlayerEntity.java` 811–817). The detector requires auto-jump enabled, no pending timer, grounded, not sneaking, not riding, and nonzero `Input.getMovement()` (823–828). It builds a direction from displacement; when squared displacement is at most `0.001F`, it derives a yaw-rotated vector from input and speed and exits if that remains at or below the threshold (830–846). It rejects movement whose dot product against look direction is below `-0.15F`, requires the immediate two collision-shape positions to be empty, then sweeps candidate collision boxes. Jump-boost amplifier raises the maximum rise from `1.2F` by `(amplifier + 1) * 0.75F`; a detected rise must be strictly greater than `0.5F` and at most the resulting limit before the timer is set to 1 (858–915). `Input.getMovement()` returns the sideways/forward pair (1.10.2 `Input.java` 18–20).
- The flying double-press branch is `!bl && input.jumping && !bl4` at 1.10.2 `LocalClientPlayerEntity.java` 719–727, so this synthetic press is excluded from starting or toggling the double-tap flight state. Scope nuance: `bl4` does not mask `input.jumping` from all other consumers; the later flying vertical-input block separately adds fly speed when `abilities.flying`, camera-controlled, and `input.jumping` (738–747). The source supports the narrower claim that the synthetic press cannot toggle flight through the double-press branch.

### Changed collision dependency boundary

`Entity.move(double,double,double)` exists at 1.9.4 `Entity.java` line 441 and 1.10.2 line 446. The extracted method bodies are both 280 lines but have different SHA-256 values (`68a47d7966cc01b5736876a485940f6a9128213e67714d21989594514be467a4` versus `b5ce7a7a8337d5e43da744ef3ab02ef4f1c80349574d699f92464c46e0b027ae`). This confirms a changed dependency at the collision/move boundary. I did not adjudicate the changed collision response; it remains outside this bounded auto-jump audit.

### No-gravity and water movement multiplier disposition

1.10.2 adds entity-level `NO_GRAVITY`, default registration, getter/setter and persistence (`Entity.java` lines 139, 192, 796–802, 1252–1254). These names/state have no matches in the 1.9.4 `Entity.java`. The 1.10.2 liquid-travel gravity subtractions are guarded by `!isNoGravity()` (1.10.2 `LivingEntity.java` 1432–1434, 1454–1456, 1482–1487). This is a later entity feature, not a changed default player response: the state is registered false.

The 1.9.4 water branch locally uses `float f = 0.8F` (`LivingEntity.java` around 1431); 1.10.2 obtains that water multiplier through `getBaseMovementSpeedMultiplier()` (1328–1330, used at 1464). The base implementation returns `0.8F`; a source-tree search finds only the polar-bear override, returning `0.98F`. A player inherits the unchanged `0.8F` default. On the ordinary false no-gravity state, these source changes do not alter the player's water multiplier or gravity response.

## Audit disposition

The report's core auto-jump finding and stated collision/no-gravity boundaries are supported by these exact mapped source trees and manifest-bound hashes. One precision note for any downstream summary: describe `bl4` as preventing synthetic input from entering the double-press flight-toggle branch; do not imply that the flag globally blocks every use of the synthetic `input.jumping` value. No pair-completion or runtime-parity conclusion follows from this bounded audit.
