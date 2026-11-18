# Provenance addendum: 1.12.2 → 26.2 Elytra near-vertical look-vector gate

## Attestation and frozen inputs

**Disposition:** The source-content identity concern raised by the independent audit is resolved for the canonical decompiled Java trees, with a qualified limit on raw-input artifact revalidation. The report’s bounded math, authoritative camera path and first already-active dry Elytra movement claim remain accepted as scoped by the audit. This addendum makes no broader movement or runtime claim.

This attestation is bound to these immutable files by their full-byte SHA-256:

| Input | SHA-256 |
|---|---|
| `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-1122-262-near-vertical-look-source\report.md` | `152a5c403175787915ed6aa6b0d3e8414375e3346538418f7db25f82617b43a0` |
| `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-1122-262-near-vertical-look-source-audit\verdict.md` | `01d64143d13d841636385eccdaf99644f3e47b10bf47effcb7d8a477e6109ec` |

Attestation signer: assigned Codex provenance-resolution worker, 2026-11-18. This is a content attestation; it is not a cryptographic signature.

## Canonical identity and manifest binding

The literal version parent and mapping child directories exist at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.12.2\ornithe-feather\` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated\`. At each version parent, the ready marker, provenance JSON, full source-hash sidecar and `artifacts.sha256` are present. The ready marker’s exact `versionId` and mapping fields agree with the parent/child path: `1.12.2` / `ornithe-feather` (Feather Gen2 build.2 mergedv2 and Tiny artifacts), and `26.2` / `unobfuscated` (published unobfuscated namespace). Both provenance records identify Vineflower 1.12.0 and Java 25.

| Version / mapping | Ready source-manifest SHA-256 | Recomputed source-sidecar SHA-256 | Ready artifact-manifest SHA-256 | Recomputed `artifacts.sha256` SHA-256 |
|---|---|---|---|---|
| 1.12.2 / ornithe-feather | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` | `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c` |
| 26.2 / unobfuscated | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |

I parsed every source-manifest entry and recomputed each file SHA-256 against the file in its declared mapping child. 1.12.2: 2,050/2,050 entries match, with no missing or mismatched entry; the Java tree is 2,050 files / 10,518,527 bytes, equal to the ready marker. 26.2: 7,055/7,055 entries match, with no missing or mismatched entry; the Java tree is 7,055 files / 33,813,717 bytes, equal to the ready marker. Thus the version/mapping metadata, ready-marker source-manifest digests, actual sidecar digests and complete present source trees bind together in this checkout.

All ten source files individually named in the frozen report were also recomputed and match both that report’s hashes and their respective complete source manifests:

| Version | Relative source path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `net/minecraft/util/math/MathHelper.java` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` |
| 1.12.2 | `net/minecraft/client/render/GameRenderer.java` | `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb` |
| 1.12.2 | `net/minecraft/client/util/SmoothUtil.java` | `006deade4fbc18ce0430b996eee58b92aee778087fc73600fa45686a6de962e6` |
| 1.12.2 | `net/minecraft/client/Mouse.java` | `b6c9132938c64f2b79138e87de60cee4b965e6c28d485798d7131ec2bf0fd058` |
| 26.2 | `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 | `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 | `net/minecraft/util/Mth.java` | `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17` |
| 26.2 | `net/minecraft/client/MouseHandler.java` | `049c4f21b6e1b724e1f0ed8b6784e55eae11c50ccd88c2a483a7253429ef8c15` |

## Qualification: original artifacts and campaign paths

The `outputPath` recorded in both ready markers points to `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\...`; that campaign directory is absent. The provenance records likewise name an absent `build\movement-campaign-2026-10-07\artifacts` cache. All 38 artifact-manifest paths for 1.12.2 and all 91 paths for 26.2 are absent under that recorded cache path. The `artifacts.sha256` files themselves are present and their full digests match the ready markers, but those text manifests do not supply the raw binaries. Therefore this review cannot rehash the client jars, Feather mapping artifacts or other original artifacts in the cache. The 1.12.2 verification JSON records no raw-input artifact differences at the time of its check and distinguishes a derived remapped-intermediate-jar hash difference; this addendum treats that as a recorded prior verification, not as a substitute for the now-missing cache. The 26.2 tree has no corresponding independent verification JSON in this location.

The success-log files and provenance metadata explicitly label their saved log as `summary-excerpt-only`; they establish a recorded successful task summary, not a retained full Gradle transcript. Do not represent them as full-run logs. In contrast, the full source sidecars and the present Java files are directly rehashed here. These limits are distinct from source-manifest integrity: the stale `outputPath` and unavailable raw inputs do not invalidate the exact hash binding of the canonical source content now examined, but they prevent a fresh raw-artifact-origin check.

## Resolved verdict

**Source identity: confirmed and independently reproducible from present local evidence.** The audit’s specific claim that the manifests were absent is superseded by the parent-level sidecars found and checked here. The evidence binds exact version/mapping labels to both full source manifests and every Java file, including all ten report-listed files. Retain the audit’s original bounded acceptance for the near-vertical math, authoritative camera-to-rotation path and first movement consumption. Qualify its provenance statement to say that raw artifact binaries and full task logs were unavailable for this recheck. No code, source tree, historical report, audit verdict, campaign output, build or runtime artifact was changed or generated.
