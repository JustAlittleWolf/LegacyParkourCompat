# Independent source identity and absence audit: Minecraft 1.8.1 → 1.8.2

## Result

The author's bounded conclusion is supported: required exact 1.8.1 Feather Gen2 build 2 Java source is not present in the named canonical source root, and no exact 1.8.1 source identity/provenance can be established there. Therefore an endpoint source comparison, no-delta verdict, paired movement inventory, finding, or pair freeze is unavailable. No source was generated, and no behavior was inferred.

## Author report identity

The reviewed report `.native-source/20261028/frontier-c/181-182-movement-boundary/source-status-1.8.1--1.8.2.md` has SHA-256 `b57254b8819826a98c16a6efad567474d26db0649ddf9836583c9550c73164d4`, matching the assigned revision.

## 1.8.1 canonical source-root check

Read-only inspection of `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.1/` with hidden entries included found zero descendants and zero immediate entries. The expected `ornithe-feather/` mapping root is absent. No `.decompile-provenance.json`, ready marker, manifest, or alternate mapping directory exists under that release parent. This supports absence at the named canonical root only; it does not establish that no source copy exists elsewhere.

## 1.8.1 input and mapping pins

The local cache artifacts rehash to the report's claims:

- Client `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.1/client.jar`: SHA-256 `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd`; SHA-1 `6edd2a3e3d76a7602c52f319f87cf11f7720b43b`. The sidecar URL points to the same Mojang object and its SHA-256 matches. Cached `version.json` identifies `1.8.1` and declares that client SHA-1; its SHA-256 is `8c6a1660665a2b1551b628f819552ed5ba0327cbb8045fb266f3fdc3e98db149`, matching its metadata pin.
- Cached Feather metadata `mapping-metadata/ornithe-feather-1.8.1.json`: SHA-256 `40754754c7133b6e01354a49e8c8ba6be2b16bac02adc881057831b6b543b7c9`; its pin agrees. The metadata selects stable `1.8.1+build.2`, game version `1.8.1`.
- Cached mapping JAR `yarn/feather-gen2-1.8.1+build.2-mergedv2.jar`: SHA-256 `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7`; its URL/SHA-256 pin agrees. The cached Tiny map SHA-256 is `f26e3fea2c580cf9eb2d5c47cd32a071f72177ba5e2c208c39196e669c8a383d` and header is `tiny 2 0 official intermediary named`.

These sidecars confirm the present local bytes against the local cache pins; they do not independently prove historical artifact origin or produce source provenance.

## 1.8.2 source-manifest and named-audit binding

The canonical 1.8.2 Feather source manifest at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.2/ornithe-feather/.decompile-provenance.json` rehashes to SHA-256 `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472`, matching the author's report. Its input entries bind the 1.8.2 client (`5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9`), Feather mapping JAR (`8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b`) and Feather metadata (`9019d76f144d8ea04b525db9e293a140cf70ca9333f056baead1391c17e1525e`). Each local artifact rehash equals both its manifest entry and its cache pin; the 1.8.2 version metadata also matches the manifest SHA-256 `305e67478038f9a3fad4cdb7699b2093261fd6301175a782220c2dfb94ae8820` and local pin.

The six movement-path source files named in the reviewed report were independently rehashed. For each, the on-disk bytes match the same relative-path entry in the embedded manifest and the report's listed hash: `Minecraft.java`, `KeyboardInput.java`, `LocalClientPlayerEntity.java`, `Entity.java`, `LivingEntity.java`, and `PlayerEntity.java`.

The explicitly named adjacent report `.native-source/20261028/frontier-c/L-full-audit/source-audit-1.8.2--1.8.3.md` has SHA-256 `c3c6a4c371868eff642ffd585b15dd7f7b6da43ae6255ca524d68f2c776f0bc6`, matching the reviewed report's cited identity. It explicitly records the same 1.8.2 manifest SHA-256. This binds that separate 1.8.2→1.8.3 audit identity to the canonical 1.8.2 manifest; it does not supply the missing 1.8.1 side of this pair.

## Bounded disposition

The reported local client/mapping input hashes and pin sidecars check out; the 1.8.2 source manifest, selected source hashes, and cited adjacent audit binding check out; the 1.8.1 canonical decompiled source identity remains absent. Preserve the report's bounded disposition: exact 1.8.1 source preparation is required before comparison. No source behavior, no-delta conclusion, applicability boundary, or pair freeze is established.
