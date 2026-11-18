# Independent verdict against the frozen source audit

Status: FAILED for complete two-edge bounded-contract closure; the bounded review is complete, but the 1.21.7→1.21.8 vanilla raw-resource dependency remains unverified.

## Reviewed immutable author inputs

The parent authorized only these two post-freeze inputs; each file was SHA-256 checked before reading:

- `source/scope-freeze.md` — `08152fcc8a085d5702a12d25b0cbe84a70a0e750c54766dff2d12c47fdd4501d`.
- `source/coverage.md` — `55295b5d9bf8aeda4882ccf9913423cd43e1c2538166adc85c144aab2afcd8c0`.

Independent evidence report: `independent-audit.md` SHA-256 `FD9313933AFD75DCE63EE61E23A9EC011CF838CD30C3E71FB8B9B0A3BE8F7E40`. Frozen roster plus append-only changes are listed in that report; the original freeze was not modified.

## Separate operation verdicts

| Operation | 1.21.6→1.21.7 | 1.21.7→1.21.8 | Independent verdict |
|---|---|---|---|
| Body water/lava contact, height, current scan/accumulation/scale | No difference; Java body and helper hashes match, and local raw client/mapping/version pins plus water/lava tag and dimension default entries verify. | No Java source difference; local source manifests and all consumed Java file hashes verify. Raw 1.21.8 client/resource bytes are absent from the standard local version cache and cannot be rehashed here; raw tags/dimension defaults remain an open gate. | Agree with author verdict: source-code slice shows no change; full edge/resource closure remains partial on 1.21.8. |
| Eye water/lava sampling and temporal state | No difference; identical Entity/Player/LocalPlayer bytes and pinned water/lava tag entries. | No Java source difference. Exact bundled 1.21.8 tag-resource bytes are not available for independent verification. | Agree with author verdict: no source difference, resource dependency open. |
| Ordinary non-ability-flight water/lava travel | No difference; `Player.travel`, `LivingEntity.travel` and their fluid helpers are identical. | No Java source difference. Caller/gates/math are established from exact matching source bytes. Raw defaults feeding the contact/height inputs remain unverified. | Agree with author verdict: no source difference; full 1.21.8 dependency closure is partial. |

No source-code contradiction or overlooked movement change was found. I independently confirmed the author coverage's caller path: on the local-player loaded path, `LocalPlayer.tick` calls `super.tick`, its `aiStep` calls `super.aiStep`, and `LivingEntity.aiStep` dispatches `this.travel` on the simulation path. The water/lava gate is tested before fall-flight/air travel. `Player.travel` handles passenger and ability-flight wrappers; the normal non-flying path delegates to `LivingEntity.travel`. Sprint/flight state transitions and equipment are excluded as specified.

## Exact remaining gates

1. The canonical 1.21.8 ready/artifact/source manifests claim exact client and mapping hashes, but the local 1.21.8 `client.jar`, `client_mappings.txt`, and `version.json` are absent from `.minecraft-decompile-cache/1.21.8`. The version-level marker supplies the ID and client digest, not independently readable raw version metadata. I did not inspect any alternate staging tree.
2. Without the exact 1.21.8 raw client bytes, these frozen members cannot be compared on 1.21.7→1.21.8: `data/minecraft/tags/fluid/water.json`, `data/minecraft/tags/fluid/lava.json`, and the four `data/minecraft/dimension_type/{overworld,overworld_caves,the_end,the_nether}.json` defaults. Do not infer their equality from identical Java code or manifest hashes.
3. Custom datapack fluid-tag membership remains conditional and outside this review.

No wiki, implementation, Git/history, research/backlog, neighboring report, network, tests, builds, or game launch was used. This verdict is bound to the exact frozen source evidence and the two hash-verified author files named above.
