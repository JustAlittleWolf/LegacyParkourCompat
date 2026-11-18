# Bounded builtin fluid-tag comparison: 1.20.1 and 1.20.2

Status: source blocker; the requested effective builtin memberships are not closed.

## Frozen authoritative input roster

| Release | Exact local source identity | Persisted exact official client archive identity | Resource payload availability |
|---|---|---|---|
| 1.20.1 | `decompiled_minecraft/1.20.1/mojmap/`; matching `mojmap.sources.sha256` (SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`); `mojmap.provenance.json` names version `1.20.1`, mapping `mojmap`, exact run dated `2026-10-07`; `mojmap.ready.json` confirms source manifest hash and release ID. | `artifacts.sha256` (SHA-256 `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163d1a2e2c9b01c7db2`) pins `1.20.1/client.jar` SHA-256 `56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f` and `1.20.1/version.json` SHA-256 `990c243ea1c716b6617839d1db1d2f2f378e8666c1a9a742b136e45d6c3039ea`. | The pinned archive payload and a same-release server archive/resource directory were not present in the inspected local source tree, `.minecraft-decompile-cache`, or `build` resource inputs. No matching `data/minecraft/tags/fluids/{water,lava}.json` was found in the source tree. |
| 1.20.2 | `decompiled_minecraft/1.20.2/mojmap/`; matching `mojmap.sources.sha256` (SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`); `mojmap.provenance.json` names version `1.20.2`, mapping `mojmap`, exact run dated `2026-10-07`; `mojmap.ready.json` confirms source manifest hash and release ID. | `artifacts.sha256` (SHA-256 `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54`) pins `1.20.2/client.jar` SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda` and `1.20.2/version.json` SHA-256 `d718d8451434d897879c96b0cac34b4c06572615be2d4c35982caa0716f2ce24`. | The pinned archive payload and a same-release server archive/resource directory were not present in the inspected local source tree, `.minecraft-decompile-cache`, or `build` resource inputs. No matching `data/minecraft/tags/fluids/{water,lava}.json` was found in the source tree. |

The `artifacts.sha256` digests match the artifact-manifest hashes recorded by the corresponding ready markers. These are persisted provenance identities for official inputs; they do not substitute for the missing resource payloads. Provenance records that the original run used a campaign-specific cache at `build/movement-campaign-2026-10-07/artifacts`, but that cache directory is absent now.

## Bounded result

Target: each release's effective **builtin/default** `minecraft:water` and `minecraft:lava` fluid-tag membership, including direct members and flowing variants, with tag includes resolved only when declared in the same exact release's builtin resources. This target excludes datapacks, mods, runtime tag reloads and non-builtin additions.

| Release | `minecraft:water` | `minecraft:lava` |
|---|---|---|
| 1.20.1 | Open: exact default tag definition/include graph and effective members cannot be verified without the exact local resource payload. | Open: exact default tag definition/include graph and effective members cannot be verified without the exact local resource payload. |
| 1.20.2 | Open: exact default tag definition/include graph and effective members cannot be verified without the exact local resource payload. | Open: exact default tag definition/include graph and effective members cannot be verified without the exact local resource payload. |

No membership or inter-release change is claimed. The missing source needed to close this bounded task is the exact 1.20.1 and 1.20.2 official client resource payload (or locally persisted exact tag-resource files with validated release provenance); a same-release official server resource payload may supply corroboration if locally available. The validated source and artifact manifests establish release/archive identities and hashes only, not resource membership. Reopen only after those resource inputs are available; inspect the exact tag JSONs and resolve their includes on each release.