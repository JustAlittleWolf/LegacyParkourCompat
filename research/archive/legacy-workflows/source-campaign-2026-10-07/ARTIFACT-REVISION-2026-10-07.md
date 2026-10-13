# Derived artifact revision, 2026-10-07

## Purpose

During source verification, six old Feather mapped jars were regenerated in the shared cache. Their original jars could not be recovered from the campaign, worktree, or Gradle caches. The original `ready/` source trees, source manifests, and raw source input artifacts remain unchanged and hash-identical. The existing ready markers and `artifacts.sha256` files retain their original values and have not been rewritten.

The revised jars are preserved separately under:

`D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\revisions\derived-artifact-snapshots\feather-r1-2026-10-07\<version>\ornithe-feather\`

Each version directory contains a read-only `client-ornithe-feather.jar`, `artifact.sha256`, and `revision.json`. The revision record links the snapshot and original derived-artifact hashes, original artifact-manifest hash, unchanged source-manifest hash, verification command, and full verification log.

| Release | Original derived-jar SHA-256 | Revised snapshot SHA-256 |
| --- | --- | --- |
| 1.8.9 | `e36a366fd30d0adda0a803a4956782bab1f67cd644e396d4db6a1cd885548c09` | `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5` |
| 1.9.4 | `0df10c862f7fd4848d08597a76c0cf02cec5a9d9886975a57230a033cfb86b3a` | `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3` |
| 1.10.2 | `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d` | `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b` |
| 1.11.2 | `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3` | `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f` |
| 1.12.2 | `65a08f15d18c4ec2bd0f05b89dfc1ba7ea6b8280ba93ed136245f39e63ec8a2b` | `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87` |
| 1.13.2 | `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5` | `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c` |

## Interpretation and consumer instructions

- The revised snapshots are new derived artifacts under immutable, versioned paths. The missing original jars are not restored.
- The decompiled Java source trees and raw input artifacts match their original published hashes. The mapped jars do not match their original hashes.
- No claim is made that the hash changes are metadata-only or that the revised mapped jars are identical to the unavailable originals.
- Before using a revised jar, independently verify its SHA-256 against both `artifact.sha256` and `revision.json`. Verify the referenced original source and raw-input records, then cite revision ID `feather-r1-2026-10-07` in any new finding snapshot that relies on this revised derivation.
- Keep the original ready marker, source manifest, artifact manifest, and verification record intact. The mutable `artifacts/<version>/client-ornithe-feather.jar` cache path is not immutable evidence and must not replace the versioned snapshot.
- All later source runs use fresh isolated caches and snapshot derived artifacts before publishing their source trees.
