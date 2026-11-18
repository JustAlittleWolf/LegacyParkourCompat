# Exact 1.12.2 → initial 1.13 glide/climb comparison

## Source identity

Compared canonical local directories `decompiled_minecraft/1.12.2/ornithe-feather/` and `decompiled_minecraft/1.13/ornithe-feather/`. The exact newer version is the stable ID `1.13`, as asserted by its adjacent ready record and provenance; this is not 1.13.2.

| Release | Adjacent ready record SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Mapping artifact identity |
|---|---|---|---|---|
| 1.12.2 | `B0AEEC721E5C0AF02B33D9E217CB8272889FA139DED1ECC2A31E42B930138F7A` | `747425A26DA363D1ABC8A2CECD83A74504AEB1F58BE93AA53F48C690E35318CE` | `B8A37CCFCCD2AAC5F40F5E34FEC85873DBDFA93A266043C103E592E4A8C949DA` | `feather-gen2-1.12.2+build.2` mergedv2 JAR and Tiny; artifact manifest SHA-256 `8171A095A5EE74CEBBD12B3D76983FC01796C0C23C962076D1E235CD0F80FA8C` |
| 1.13 | `82A4A8F6A03FBD99A7B8577B1AE5980D95AF07C7DED0DA284106D16EB1E01A99` | `34652AEEF6914B5CBFF6619AE60A855AA676B2AE1F607ECCDAA623AD09DC5E8D` | `0509C2614B0BB0E616E2EBC6382F8EB5D031B5D86B834A4A6ED7FD96B6D68F1A` | `feather-gen2-1.13+build.2` mergedv2 JAR and Tiny; artifact manifest SHA-256 `A2C17075B601D9B83703F7EE188B04C6D9F68CAC08D97B1A4F838FE8CD2E2C24` |

The version-root `ornithe-feather.ready.json`, `ornithe-feather.provenance.json` and `ornithe-feather.sources.sha256` (plus the artifact manifest) are present for both releases. The 1.13 ready record says `versionId: 1.13`, `mapping: ornithe-feather`, and names these adjacent manifests; its source-manifest and provenance digests match the actual adjacent files. No provenance/ready/source manifest is hidden inside either `ornithe-feather/` mapping directory. I independently matched the six cited source files per release byte-for-byte against their adjacent source-manifest SHA-256 entries. The 1.13 provenance identifies mapping coordinate `net.ornithemc:feather-gen2:1.13+build.2`, exact version metadata ID `1.13`, client SHA-256 `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e`, Vineflower `1.12.0`, and Java `25.0.3+9-LTS`.

### Compared source-file digests

| Release | File under `ornithe-feather/` | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01A58E94D8C6FF98A8E3794227CDC76A5FCBDBAD795C70C9CF28854AFF9823CC` |
| 1.13 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `A188E1BC87831DD9DA1EC1366A49ACC4104C18E6979129097D02D5D3617E6A08` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190E9AC551538E015D9E4D6C42856E5BA32B593131CF6D93895E7B29533F1EE6` |
| 1.13 | `net/minecraft/entity/living/LivingEntity.java` | `C38E95C2C13E088B03D77FC2C6B98D4EDD7E6CA5AEF12B187C8E3F3EA782E864` |
| 1.12.2 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `77BF65B2C48FF952713942E183AF1CD5FB243AD4F8FD2E53AA1B97272E7ACD7C` |
| 1.13 | `net/minecraft/server/network/handler/ServerPlayNetworkHandler.java` | `099EC9517B23D3A664BAD796DA840185BD4FA0050F33B22DEE291844981E16CA` |
| 1.12.2 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `AD4EFFC65EDD98098D86AF8BA725F6D6C2C94068F996739CF2734D34ECF804A9` |
| 1.13 | `net/minecraft/server/entity/living/player/ServerPlayerEntity.java` | `4D529141F91F9EF5C71FCD87E68C16C666E049363A305FB98F0A19B3CA730EB3` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` |
| 1.13 | `net/minecraft/entity/Entity.java` | `09514488D9055449C47E2E4BCD23B5773203CA634B7ECBA2323A99D0AE016642` |
| 1.12.2 | `net/minecraft/item/ElytraItem.java` | `16E02A70181C063C0643B7DFDBE17516AD46CD38053A2D88CC3FB97400652C7C` |
| 1.13 | `net/minecraft/item/ElytraItem.java` | `4FF72D8153400A0CE484C568B899A2B8C23C94654BD7C3F5DD38568DA3DCF72B` |

## Bounded claim: dry, already-active local glide over ladder/vine

Scope: ordinary local-player tick, dry with respect to both travel fluid gates, default usable chest Elytra, flag 7 already true, no ability flight or slow-falling effect, with ladder or vine at the block selected by the feet-position predicate. Compared request/flag semantics for context, ongoing flag authority, travel precedence, the pre-first-move fall-distance operation and climbing predicate. This is not a full pair inventory.

**Result:** the active glide/climb alias gate and the thresholded pre-first-move `fallDistance` write match between 1.12.2 and initial 1.13. In both `LocalClientPlayerEntity.tickMovement`, the new glide request has the same jump-edge, airborne, negative-vertical-velocity, inactive-fall-flying, non-ability-flight and chest-Elytra-usability gates (`1.12.2:764–769`; `1.13:773–778`). It sends `START_FALL_FLYING` but does not set flag 7 locally; the immediate `falling = isFallFlying()` assignment reads the current bit (`1.12.2:771`; `1.13:780`). `isFallFlying()` returns `getFlag(7)` in both (`LivingEntity:2184–2186`; `1.13 LivingEntity:2295–2297`). `ElytraItem.canFly` is the same test, `damage < maxDamage - 1` (`1.12.2:29–31`; `1.13:20–22`).

The server owns activation: both `START_FALL_FLYING` handlers require airborne, descending, not already fall-flying and not in water, then validate the chest Elytra and call `setFlying()` (`1.12.2 ServerPlayNetworkHandler:877–882`; `1.13:1090–1095`). Both `ServerPlayerEntity.setFlying()` methods set flag 7 true (`1.12.2:1188–1190`; `1.13:1179–1181`). During travel, `LivingEntity.flyingTick()` revalidates flag 7, airborne/not-riding state and usable chest Elytra; only the logical server writes the resulting flag (`1.12.2:1854–1872`; `1.13:1919–1937`).

For the dry witness, both `moveRelative` methods test the water gate and lava gate, then route `isFallFlying()` into glide before entering the ordinary movement branch with `isClimbing()` (`1.12.2:1425–1478`; `1.13:1459–1518`). Thus an already-active glide bypasses ladder/vine velocity clamps, the climb `fallDistance = 0.0F` write, sneaking stop and post-move climb kick. The pre-move glide threshold is identical: `if (velocityY > -0.5) fallDistance = 1.0F` before glide math and `move()` (`1.12.2:1429–1432`; `1.13:1469–1472`). The same `isClimbing()` predicate in both floors x/z and `getShape().minY`, rejects spectators, then reads ladder/vine block identity or an eligible trapdoor (`1.12.2:1006–1018`; `1.13:1041–1053`).

For the A/B input boundary, water and lava must both be absent to take the glide branch unless ability flight applies. `isInWater()` reads the maintained `inWater` field in both (`1.12.2 Entity:940–942`; `1.13:915–918`). Lava contact geometry differs: 1.12.2 checks `getShape().grown(-0.1F, -0.4F, -0.1F)` (`Entity:1045–1047`), while 1.13 uses `getShape().contract(0.1F, 0.4F, 0.1F)` (`Entity:1052–1054`). Those contact differences are outside this dry witness. The feet-position ladder/vine predicate uses the player box's `minY` and block identity in both, not a ladder/vine collision-shape substitution.

The source operation `fallDistance = 1.0F` is therefore not a version delta in this pair or a consequence of climbing. Keep it separate from the adjacent glide-math difference below.

## Separate glide-math delta

The verified initial 1.13 source also changes the glide computation: 1.12.2 uses `velocityY += -0.08 + m * 0.06` (`LivingEntity:1439–1441`); 1.13 uses `velocityY += d * (-1.0 + n * 0.75)` with `d = 0.08` in this no-slow-falling witness (`:1461–1481`). The source expression changes floating-point operation order. Separately, 1.12.2's negative-pitch correction only checks `i < 0.0F` and divides by horizontal look length (`:1449–1453`); 1.13 adds `k > 0.0` to that gate (`:1489–1494`). This avoids that correction's zero-horizontal-look division at the vertical-look boundary. These are glide math observations, separate from the matched pre-move fall-distance operation, and need their own finding/review if pursued.

## Coverage and limits

Checked the exact version/mapping/provenance identity; source-file digests against the adjacent manifests; client launch gates and immediate local flag read; server flag authority and validator; dry branch precedence; feet-block ladder/vine predicate; pre-move threshold assignment; Elytra wear/usability condition; water/lava gate inputs and lava geometry; adjacent glide expression and zero-horizontal-look gate. Not checked: all tick dependencies, all fluid transitions, non-dry flight, other movement effects or full-pair coverage. No product/wiki sources, Git history, network access, source regeneration, build, test or launch was used.
