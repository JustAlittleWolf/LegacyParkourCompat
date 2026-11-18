# Local canonical publication pin recovery addendum

Read-only provenance recovery; no network was used and no inputs were replaced. The original `determinations.md`, `artifact-pins.json`, and `resource-roster.json` remain unchanged. Comparisons use only the exact client JAR and raw version JSON already captured in this scratch directory.

## Canonical sidecars inspected

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.10\artifacts.sha256` (SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`)
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.10\mojmap.ready.json` (SHA-256 `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`)
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.10\mojmap.provenance.json` (SHA-256 `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c`)
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.11\artifacts.sha256` (SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`)
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.11\mojmap.ready.json` (SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`)
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.11\mojmap.provenance.json` (SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`)

Both ready markers' `artifactManifestSha256` equal the actual `artifacts.sha256` file SHA-256 above. All six named sidecars exist.

## Exact pin comparisons

| Release | Captured client JAR SHA-256 | Canonical `client.jar` SHA-256 | Result | Captured raw `version.json` SHA-256 | Canonical `version.json` SHA-256 | Result |
| --- | --- | --- | --- | --- | --- | --- |
| 1.21.10 | `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482` | `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482` | Match | `3680286cdcfde9f4c540938a20d7fe917f5f189bf326791f6a9fc9d8798aea29` | `15faa7deb7cd39479db123ad4664e2fb83e05d4729b7e4a8ff813107376e349a` | Mismatch |
| 1.21.11 | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | Match | `471a6eeadf1c74f84ed5d4290fb7234b746373478a4d6ac651962b93f0cb36d9` | `9b55607ecf8a37c9cd5b7d17ffb5c283d7c8ca4333b34ef8bb20dcd24419b3a8` | Mismatch |

The matching canonical client pins are independently repeated by each `mojmap.ready.json` `clientSha256`. Captured metadata SHA-1 values remain `b14d03617088d907aef01138b042e8e5c1ab35d4` (1.21.10) and `718cd84b4912691fe5de829aab6876a4b7f6c5c4` (1.21.11), matching the exact manifest-published metadata identities used at capture. The canonical artifact manifests supply only a different raw SHA-256 for each `version.json`; their corresponding canonical metadata bytes and SHA-1 values are not present among the inspected sidecars or the previously captured inputs. Therefore there is a confirmed raw metadata hash mismatch against the canonical published artifact identity, but no basis here to identify the differing content or assert which metadata fields changed. No client artifact mismatch exists.
