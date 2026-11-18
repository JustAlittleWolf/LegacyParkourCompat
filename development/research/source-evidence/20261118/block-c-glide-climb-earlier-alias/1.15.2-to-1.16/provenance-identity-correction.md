# Provenance identity correction — 1.15.2 → 1.16 Elytra glide audit

## Binding

This signed correction binds the bounded source audit to the exact frozen inputs below. It does not modify either input.

- Frozen source report: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-climb-1152-116-alias-gate\report.md` — SHA-256 `0a699ed1a52bd2f6df1f853240c8ddb7dad557d07e6fb012e195a51c75502b5c`.
- Independent audit verdict: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-climb-1152-116-alias-gate-audit\verdict.md` — SHA-256 `894d932c9eb71aa81e2b245d3ff6cf32c6b8ef1d1c7550304398edb03a1ab63e`.

## Corrected 1.16 marker identity

The canonical marker is `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16\mojmap.ready.json`. Its SHA-256 computed from the file bytes is:

`5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2`

The frozen source report lists `5b6cb24f95e83b856c53f646d6477f2edae2276737dee56dd07c5bd745e4a2`, which does not match those bytes. This is a ready-marker hash transcription error. The older marker is `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap.ready.json`, SHA-256 `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`, matching the frozen report.

## Matching source pins checked

The corrected 1.16 marker declares source-manifest SHA-256 `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`. Independently hashing canonical `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16\mojmap.sources.sha256` produces the same SHA-256. The marker declares exact version `1.16` and mapping `mojmap`.

For the older side, the 1.15.2 marker declares source-manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`, matching an independent hash of `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap.sources.sha256`; the marker declares version `1.15.2` and mapping `mojmap`.

The four relevant Java file hashes were independently recomputed and matched their entries in the pinned manifests:

- 1.15.2 `mojmap/net/minecraft/world/entity/LivingEntity.java` — `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`.
- 1.15.2 `mojmap/net/minecraft/world/item/ElytraItem.java` — `701e25f13e9ba8db74aca774f404a5282a58f4f7dc72e3aeabac76bbf28b43`.
- 1.16 `mojmap/net/minecraft/world/entity/LivingEntity.java` — `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`.
- 1.16 `mojmap/net/minecraft/world/item/ElytraItem.java` — `d20a679b8822eaa57e5216ce14ed755c1f86a160eb5dad0dc9e3d8f4b5ea018d`.

## Acceptance and raw-artifact boundary

**The bounded source-behavior acceptance stands using this corrected sidecar identity.** The incorrect 1.16 marker transcription does not change the version, mapping, source-manifest pin, or any of the four checked source bodies. The accepted boundary remains the verdict's ordinary, dry, on-foot local player, already fall-flying, enabled chest Elytra, ladder/vine feet, and no-Levitation case. This does not freeze the whole pair or establish runtime parity.

The 1.16 marker also records client artifact SHA-256 `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71` (and artifact-manifest SHA-256 `b482d7aa44422dfafebad3a0b2a886d47d1f01135f99055aea5973faa110e8b2`). Those are marker metadata; this correction independently hashes the marker, source manifests and relevant decompiled Java files. It does not independently retrieve or recompute the original client JAR/raw artifact bytes, and makes no new raw-artifact verification claim.

The frozen report remains byte-for-byte unchanged and still contains the erroneous marker hash. If the acceptance record requires the source report itself to be internally self-consistent, its author must issue a corrected immutable report revision or an authorized erratum bound to the frozen report SHA above. This sidecar supplies that erratum identity; it cannot rewrite or change the SHA of the frozen report.

## Signed attestation

I attest that the hashes above were computed from the named local files and that the bounded source acceptance remains supported under the corrected marker identity.

Signed by: Codex independent source-audit worker C  
Date: 2026-11-18 UTC
