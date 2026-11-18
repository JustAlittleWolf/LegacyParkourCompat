# Independent packaged fluid tag audit

Pair: Minecraft 1.21.5 → 1.21.6. The independent roster was frozen in `frozen-roster.md` before opening the authored comparison and manifest.

## Verdict

**High confidence for the bounded shipped-resource claim:** direct ZIP inspection verifies that `data/minecraft/tags/fluid/water.json` and `data/minecraft/tags/fluid/lava.json` are present at identical paths and byte-identical in both exact client archives. Ordered values match for each; `replace` is absent; there are no included tag references. Neither archive contains `data/minecraft/tags/fluids/` (plural), and no additional files exist under `data/minecraft/tags/fluid/`. There is no 1.21.5 → 1.21.6 packaged default fluid-tag difference in this roster.

This verdict covers only default client ZIP resources. It does not establish datapack overlays or runtime tag resolution, and it is not a complete audit of fluid movement code.

## Exact source identity and pin caveats

The 1.21.5 raw metadata has SHA-1 `b341cd0e91419ab72b5d9dadad8d86a045d6b9a7`, equal to the 1.21.5 entry in the locally captured Mojang release catalog. The catalog’s URL is `https://piston-meta.mojang.com/v1/packages/b341cd0e91419ab72b5d9dadad8d86a045d6b9a7/1.21.5.json`. Its SHA-256 is `1751e36c5dda2bbf847d543d4f9a59c51035fe474b8528aca7f76c688f32fe88`. There is no local 1.21.5 `version.json.pin.json`; the catalog itself is a mutable discovery index, so this is a current catalog corroboration, not a local metadata pin.

Despite that old local pin absence, 1.21.5 archive identity is directly verified against the official metadata: actual size `28984409` equals declared size; actual SHA-1 `b88808bbb3da8d9f453694b5d8f74a3396f1a533` equals declared SHA-1; archive SHA-256 is `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`.

For 1.21.6, raw metadata SHA-1 `8dd125560431a618094435343acccd45191bb006` matches the local release-catalog entry; metadata SHA-256 `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd` matches `.minecraft-decompile-cache/1.21.6/version.json.pin.json`. The archive is `29516555` bytes, matching official metadata; SHA-1 `740a125b83dd3447feaa3c5e891ead7fbb21ae28` matches official metadata; SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` matches `.minecraft-decompile-cache/1.21.6/client.jar.pin.json`.

The existing 1.21.5 decompile ready marker and artifact manifest were checked directly. The marker SHA-256 is `5f65fb143209abcc6e28e77a7150aecefdbc9de00b6ef3fc924e53bc0e1a667e`; its referenced `artifacts.sha256` SHA-256 matches marker value `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`. The client row matches the directly verified raw client SHA-256. However, the manifest's `1.21.5/version.json` row says `4a9a484109ee5c3f430b685ddc719712e962e8d06c761cd58ec0a1e6fea2f18c`, which differs from the actual SHA-256 of official metadata bytes whose SHA-1 matches the catalog. This older artifact-manifest metadata digest discrepancy is unresolved, but it does not change the independently verified client archive identity or the resource bytes extracted from it.

No `decompiled_minecraft/1.21.6/mojmap.ready.json` exists in this checkout. The missing source publication marker is separate from the verified 1.21.6 official metadata, local pins and raw archive identity; no decompiled-source readiness claim is made.

## Reconciliation against authored artifacts

After the roster freeze, the specified files were opened read-only. Both actual SHA-256 values match the supplied identities:

- `comparison.md`: `9ae60c1f8c0871e91205cb44b1ebfffce8499c8d52edb57c183a8aa12c8a8c4c`.
- `manifest.json`: `96c238b0d5824578fff010c65f23a6fdb8b89c2b631657a2218b49d70c4491a9`.

Their exact four-entry inventory, paths, raw hashes, `replace` absence, direct ordered members and negative plural-path check agree with my frozen roster. I found no disagreement in the bounded resource comparison. The author report's 1.21.5 older artifact-manifest metadata discrepancy and 1.21.6 missing ready marker are also directly confirmed as caveats, separate from raw archive identity.
