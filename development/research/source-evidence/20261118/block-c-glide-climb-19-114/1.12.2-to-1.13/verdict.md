# Independent source audit verdict

## Binding

- Claim reviewed: bounded dry, already-active local Elytra glide while the feet-position block is a ladder or vine; plus the separately reported glide-math observation.
- Input: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-climb-1122-113-alias-gate/exact-1.13-comparison.md`
- Input SHA-256: `AE88EA7B880C1DC0D1A6EEB0E8FA7ABE772A8719446247E69B39203EE0627A57`
- Verdict scope: independent review of the named bounded claim only. This does not accept full-pair coverage, a general Elytra implementation, or runtime parity.

## Exact local source identity and bytes

The local Feather roots are `decompiled_minecraft/1.12.2/ornithe-feather/` and `decompiled_minecraft/1.13/ornithe-feather/`. The ready records identify exact IDs `1.12.2` and `1.13`, namespace `ornithe-feather`; the latter is the initial stable `1.13` release, not `1.13.2`. The 1.13 provenance records `net.ornithemc:feather-gen2:1.13+build.2`; 1.12.2 records the matching `feather-gen2-1.12.2+build.2` artifacts.

| Release | Ready-record SHA-256 | Provenance SHA-256 | Source-manifest SHA-256 | Artifact-manifest SHA-256 |
|---|---|---|---|---|
| 1.12.2 | `B0AEEC721E5C0AF02B33D9E217CB8272889FA139DED1ECC2A31E42B930138F7A` | `747425A26DA363D1ABC8A2CECD83A74504AEB1F58BE93AA53F48C690E35318CE` | `B8A37CCFCCD2AAC5F40F5E34FEC85873DBDFA93A266043C103E592E4A8C949DA` | `8171A095A5EE74CEBBD12B3D76983FC01796C0C23C962076D1E235CD0F80FA8C` |
| 1.13 | `82A4A8F6A03FBD99A7B8577B1AE5980D95AF07C7DED0DA284106D16EB1E01A99` | `34652AEEF6914B5CBFF6619AE60A855AA676B2AE1F607ECCDAA623AD09DC5E8D` | `0509C2614B0BB0E616E2EBC6382F8EB5D031B5D86B834A4A6ED7FD96B6D68F1A` | `A2C17075B601D9B83703F7EE188B04C6D9F68CAC08D97B1A4F838FE8CD2E2C24` |

I independently hashed the six source files per release and matched each digest to the corresponding adjacent source-manifest entry. These are the actual file-byte SHA-256 values:

| Relative source file | 1.12.2 | 1.13 |
|---|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01A58E94D8C6FF98A8E3794227CDC76A5FCBDBAD795C70C9CF28854AFF9823CC` | `A188E1BC87831DD9DA1EC1366A49ACC4104C18E6979129097D02D5D3617E6A08` |
| `net/minecraft/entity/living/LivingEntity.java` | `190E9AC551538E015D9E4D6C42856E5BA32B593131CF6D93895E7B29533F1EE6` | `C38E95C2C13E088B03D77FC2C6B98D4EDD7E6CA5AEF12B187C8E3F3EA782E864` |
| `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `77BF65B2C48FF952713942E183AF1CD5FB243AD4F8FD2E53AA1B97272E7ACD7C` | `099EC9517B23D3A664BAD796DA840185BD4FA0050F33B22DEE291844981E16CA` |
| `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `AD4EFFC65EDD98098D86AF8BA725F6D6C2C94068F996739CF2734D34ECF804A9` | `4D529141F91F9EF5C71FCD87E68C16C666E049363A305FB98F0A19B3CA730EB3` |
| `net/minecraft/entity/Entity.java` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` | `09514488D9055449C47E2E4BCD23B5773203CA634B7ECBA2323A99D0AE016642` |
| `net/minecraft/item/ElytraItem.java` | `16E02A70181C063C0643B7DFDBE17516AD46CD38053A2D88CC3FB97400652C7C` | `4FF72D8153400A0CE484C568B899A2B8C23C94654BD7C3F5DD38568DA3DCF72B` |

The ready records' manifest digests agree with the bytes of the adjacent manifests. These checks establish local source/provenance identity; they do not establish decompiler reproducibility or movement parity outside the reviewed slice.

## Bounded claim verdict: matched

**Accepted for this witness:** the already-active dry glide branch precedes and bypasses ladder/vine climbing cleanup in both releases, and the thresholded `fallDistance = 1.0F` write immediately precedes glide math and `move()` in both. No source delta is present in this narrowly bounded gate.

- The local request branch has matching conditions: jump input, prior jump-state guard, airborne, descending, not already fall-flying, not ability-flying, and a usable chest Elytra. It sends `START_FALL_FLYING`; it does not set shared flag 7 locally. The immediate `falling = isFallFlying()` reads flag 7 (`LocalClientPlayerEntity.java`: 1.12.2:764–771; 1.13:773–780). `isFallFlying()` returns `getFlag(7)` in both (`LivingEntity.java`: 1.12.2:2184–2186; 1.13:2295–2297). The usability predicate is the same strict durability comparison, `damage < maxDamage - 1` (`ElytraItem.java`: 1.12.2:29–31; 1.13:20–22).
- Activation authority is consistent: each server packet handler checks airborne, descending, not already fall-flying, not in water, and valid chest Elytra before calling `setFlying()` (`ServerPlayNetworkHandler.java`: 1.12.2:877–882; 1.13:1090–1095). `ServerPlayerEntity.setFlying()` sets flag 7 true in both (1.12.2:1188–1190; 1.13:1179–1181). During travel, `LivingEntity.flyingTick()` validates the existing flag, airborne/not-riding state and Elytra usability; it writes the recomputed flag only when `!world.isClient` (1.12.2:1854–1872; 1.13:1919–1937).
- In `LivingEntity.moveRelative`, the witness reaches glide only after the local/update-authority check and both water and lava gates. Once `isFallFlying()` is true, that branch precedes the ordinary branch containing `isClimbing()`, its horizontal clamps, `fallDistance = 0.0F`, sneak stop and post-move climb kick (1.12.2:1425–1478; 1.13:1459–1518). The glide branch tests `velocityY > -0.5`, writes exactly `1.0F`, then obtains look vectors and later calls `move()` (1.12.2:1429–1464; 1.13:1469–1504). Thus climbing cleanup does not supersede the pre-move glide write for this already-active witness.
- The climbing predicate itself matches for ladder/vine identity: it floors x, feet `getShape().minY`, and z; rejects player spectators; then checks ladder/vine before the trapdoor case (1.12.2 `LivingEntity.java`:1006–1018; 1.13:1041–1053). It reads vanilla block identity; no block-shape replacement is implicated.

**Witness boundary:** local controlled player; flag 7 already true; dry at both fluid gates; usable Elytra in chest slot; no ability flight or slow-falling effect; ordinary ladder/vine at the feet-position predicate. This does not cover flight activation latency, wet/lava behavior, slow falling, other travel branches, all tick dependencies, or complete pair coverage. The 1.13 slow-falling prelude sets `d = 0.01` and resets fall distance when its own gate applies (1.13 `LivingEntity.java`:1461–1465); it is excluded from this no-slow-falling witness.

## Separate glide-math observation

This is a distinct change from the matched alias/threshold gate and should be reviewed/recorded as its own operation. In the no-slow-falling witness, 1.12.2 adds `-0.08 + m * 0.06` to vertical velocity (`LivingEntity.java`:1434–1441), after calculating `m` from pitch cosine and look-vector length. Initial 1.13 initializes `d = 0.08`, then adds `d * (-1.0 + n * 0.75)` (`LivingEntity.java`:1461–1481). The constants and floating-point expression order differ; do not fold this into the climbing cleanup or `fallDistance` claim.

There is also a separate negative-pitch horizontal-look gate change. 1.12.2 enters that correction on `i < 0.0F`, then divides its x/z adjustments by horizontal look length `j` (lines 1449–1453). 1.13 requires both `j < 0.0F` and horizontal length `k > 0.0` before the analogous division (lines 1489–1494). For an exactly zero horizontal look vector, the latter skips this correction; the former has zero denominators. This observation is bounded to the correction's gate and math and makes no claim about all vertical-look trajectories.

## Independent disposition

- Bounded active-glide/climb alias and pre-move threshold claim: **confirmed matched** for the witness above.
- Separate gravity expression and negative-pitch zero-horizontal-look guard: **confirmed source delta**, separate finding/review required.
- Full 1.12.2→1.13 source-pair coverage: **not audited here**.
- Product source, Git history, wiki inventory, network, generation, builds, tests and launches: **not used**.