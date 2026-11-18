# C audit verdict: 1.15.2 → 1.16 raw pitch setter

## Verdict

**No difference is present in the bounded ordinary local `Entity.turn` pitch operation or in the next dry active-local Elytra pitch read.** The paired `turn` bodies have the same statements and order: form `f = e * 0.15` as `double`; add it to current `xRot` and narrow the `double` sum to `float`; clamp current pitch to `[-90.0F, 90.0F]`; then add the same `f` to `xRotO`, narrow, and clamp the previous pitch to the same interval. Yaw writes remain between the current and previous pitch updates. This ordering is identical in 1.15.2 `Entity.turn` (326–338) and 1.16 `Entity.turn` (332–344).

There is **no reachable finite `xRot` or `xRotO` value above 360 degrees through this ordinary direct-turn path**: after each update both pitch fields are independently clamped. A finite huge delta may narrow to an infinity, which the clamp bounds; a NaN remains NaN and is not a finite witness. This verdict does not claim every possible direct field write or arbitrary programmatic call to `Entity.moveTo` is bounded.

For active local play, `LocalPlayer.isEffectiveAi()` returns `true` in both releases (1.15.2 490–492; 1.16 519–521), so `LivingEntity.travel` enters its AI-controlled path. In 1.15.2, dry means the water and lava guards pass; when fall-flying, travel reads current `xRot` as `xRot * (float)(Math.PI / 180.0)` (1848–1850). In 1.16, the equivalent dry water/lava `else if` chain reaches the same fall-flying branch and reads current `xRot` using the same expression (1983–1985). The local-player current-pitch consumer and its arithmetic therefore show no paired difference. This read is not a read of `xRotO`.

As a check on a possible network-fed finite >360 witness, both client position-correction handlers pass the received pitch to `player.absMoveTo` (1.15.2 `ClientPacketListener` 669–727; 1.16 683–745). In both releases `Entity.absMoveTo` clamps pitch to `[-90, 90]` before `% 360.0F`, then copies it to `xRotO` (1.15.2 1094–1105; 1.16 1104–1115). Thus that ordinary correction path also supplies no >360 witness. An arbitrary external direct write is outside this bounded local-input/correction audit.

## Exact source identity and provenance

Both compared trees are exact version IDs under the `mojmap` namespace. Their ready markers identify the release and source manifest; each manifest digest equals both the marker's `sourceManifestSha256` and the actual SHA-256 of the manifest file. Every relied Java source digest below matches its entry in that manifest and a fresh SHA-256 of the file.

| Release | Ready source inventory | Artifact manifest | Raw client | Mojang mappings |
|---|---|---|---|---|
| 1.15.2 | 3,332 files; SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | SHA-256 `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` | SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` | `client_mappings.txt` SHA-256 `65ad295b6cf63821f5d8f961c128128475e6d39386358d22eb238a3ce6e31777` |
| 1.16 | 3,572 files; SHA-256 `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` | SHA-256 `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2` | SHA-256 `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71` | `client_mappings.txt` SHA-256 `53d46abe149aadd367be8789207c53a7cee46ba8382e5bfd60247eb22984119d` |

The 1.15.2 ready marker's SHA-256 is `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`; its provenance record is `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`. It records Java 25.0.3, Gradle 9.7.1, Vineflower 1.12.0, Tiny Remapper 0.14.1, Mapping IO 0.9.1, Gson 2.14.0 and ASM 9.10.1. Its artifact manifest pins `version.json` at `e974511243e845b2427ab635eba5612481cb420a70c0630aa041eab3eeb590c5`, mapped client JAR at `c0fe9cfe2a273c42ff269b58c9aabb93815d6bb7bf457ca1deb8695833c4380b`, and the inputs above. This older provenance record has `mappingArtifacts: null` and only a summary-excerpt success log; the artifact manifest itself supplies the mapping-file hash and exact version ID.

The 1.16 ready marker SHA-256 is `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2`; its provenance SHA-256 is `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9`. It records resolved release `1.16`, the exact version-metadata, raw-client, mapping and mapped-client hashes, and the same decompiler tool versions. The recorded raw-client hash agrees with the ready marker and artifact manifest.

The referenced raw/mapped client and mapping artifact paths are not present in this checkout's campaign cache, so those binary bytes could not be rehashed here. The published ready/provenance/artifact manifests and their source inventories are internally consistent; the source files used for this verdict were independently rehashed and match the inventories.

## Relied decompiled-source SHA-256

| Release | Relative source file | SHA-256 |
|---|---|---|
| 1.15.2 | `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15.2 | `net/minecraft/world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| 1.15.2 | `net/minecraft/client/player/LocalPlayer.java` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| 1.15.2 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |
| 1.16 | `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| 1.16 | `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| 1.16 | `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |
| 1.16 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d` |

## Phase-two reconciliation: finite transient and input gate

The frozen opening sentence above is narrowed by this addendum: it describes the **post-call persisted state**, not the intermediate result of the first pitch assignment. The source does allow a finite transient pitch above 360 before the immediately following clamp. The float narrowing is part of the double-sum field assignment; it can produce a finite float above 360 without overflowing. The subsequent clamp stores a value in `[-90, 90]`. The same sequence applies independently to `xRotO`; neither field persists the transient and the next Elytra read consumes current `xRot` only after `turn` completes.

A finite source-reachable mouse-input witness exists under the ordinary active local input route. In each release, `MouseHandler.onMove` first requires the active window ID and accumulates cursor delta only when the mouse is grabbed and the window is active; the first move resets the baseline. `turnPlayer` requires grabbed + active again, computes `f = sensitivity * 0.6F + 0.2F`, then `g = f * f * f * 8.0`; with smooth camera off it uses `accumulatedDY * g`, clears the accumulator, applies the optional Y inversion and calls `player.turn(j, k * n)` when the player exists. The ordinary sensitivity slider permits `[0, 1]`; at its maximum, the code's float literals make `g` approximately `4.0960004`. With a prior current and previous pitch of zero, a matching-window active grabbed cursor event whose post-baseline vertical delta is finite `600.0`, no inversion, smooth camera off, sensitivity `1.0`, and a non-null local player yields `k ≈ 2457.6002`; `turn` computes `f ≈ 368.6400`. The first pitch field assignment therefore transiently stores finite `368.64F` (above 360) before the clamp immediately stores `90.0F`. The previous-pitch assignment likewise transiently exceeds 360 before its clamp stores `90.0F`.

This proves the client source accepts a lawful finite cursor-event value through the ordinary gate and contains no Java-side numeric cursor-delta cap on that route. It is a source-level reachable witness, not a claim about what every OS/device emits in one callback. It also does not change the next-step conclusion: the ordinary local dry active Elytra branch reads `xRot` after the setter has clamped it, so this witness supplies `90.0F`, not `368.64F`, to the Elytra pitch math. Paired `Entity.turn`, clamp ordering, mouse scaling/gates, and this persisted pitch remain identical in the two source sets.

Additional relied source lines and SHA-256s (each actual hash matches its release source-manifest row):

- 1.15.2 Mojmap `MouseHandler.java:155–228`, SHA-256 `925ef4e75c1da041e078eb71b6bc79180ebbb1329ce9d18efb5203777fd1fe5b`; `Option.java:143–160`, SHA-256 `cc99ebf818f3400bb316769026fba4fa3983ceb0f017cb247802dd7f8e46f6d8`.
- 1.16 Mojmap `MouseHandler.java:175–247`, SHA-256 `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949`; `Option.java:204–222`, SHA-256 `1fc5a6e4204306002789f487d52932c6fba2782c96e38bbc295ff4219eaecc27`.
- Clamp evidence remains the paired `Mth.java` digests already listed in the source report: 1.15.2 `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4`; 1.16 `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290`.

The author report compared for this phase was the exact requested file with SHA-256 `021ade5149924a90d72406f2d9502e6e0e81eb1914c66ae39c503b9cbbc2dcb3`. No additional author artifacts were opened.