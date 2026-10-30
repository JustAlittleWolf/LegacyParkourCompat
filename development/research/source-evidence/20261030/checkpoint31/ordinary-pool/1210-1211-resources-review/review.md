# Independent review: packaged resources, 1.21.0 → 1.21.1

## Immutable inputs

- Reviewed report: `.native-source/20261030/ordinary-pool/1210-1211-resources/report.md`, SHA-256 `59b727aa2e74d6c71927cad0b634cbfac2689fda09ebcda1e0b751b149685ec0` (matches assigned pin).
- Source audit identity: `.native-source/20261030/1210-1211-audit/source-audit.md`, SHA-256 `0c23a664552dd5fcfdfe12d715dfd3cfc25efcaba2ccef3eb12b3e3dc2f27b1d` (matches assigned pin).

## Verdict

The resource report is independently confirmed for its bounded claim: the exact 1.21.0 and 1.21.1 client archives contain equal packaged movement-selection inputs when compared by uncompressed entry contents. No correction to its inventory totals or tag/enchantment results was found. Its stated open gates correctly leave source-pair coverage and runtime parity unresolved.

## Identity and manifest checks

Recomputed SHA-256 values match the report for both client jars, both version metadata files, the old embedded provenance file, and the four 1.21.1 ready/provenance/artifact/source manifest files. The two metadata IDs are `1.21` and `1.21.1`. The old client and metadata hashes match their cache pin files and the old embedded provenance inputs. The old provenance also pins the reported `client.txt` mapping input. The new ready marker agrees with the new client hash, version ID, `mojmap` mapping, 5,363 source count, and the hashes of `mojmap.sources.sha256` and `artifacts.sha256`; the artifact manifest contains the matching `1.21.1/client.jar` row. The source manifest has 5,363 rows. No decompilation or download was performed.

## Independent archive results

| Check | Result |
|---|---:|
| Non-directory entries, each jar | 23,519 |
| Added / removed entries | 0 / 0 |
| Entries with differing uncompressed contents | 1,216 |
| Changed `data/` entries | 1,180 |
| Changed `assets/` entries | 0 |
| Changed data paths outside `data/minecraft/structure/**/*.nbt` | 0 |
| Block / fluid / item / entity-type / enchantment tag JSON files | 184 / 2 / 147 / 34 / 22, all identical |
| All vanilla tag JSON files | 530, all identical |
| Enchantment / damage-type definition JSON files | 42 / 47, all identical |

## Open gates

No additional resource-specific gate was found. As the report states, this review does not close complete movement-source coverage, changed callback descendants, generated-world equivalence, custom data-pack behavior, or runtime parity. Those remain outside the confirmed bounded resource comparison.
