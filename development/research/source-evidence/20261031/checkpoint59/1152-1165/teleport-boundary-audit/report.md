# Teleport-coordinate fallback boundary audit

## Result

The handler body changes from the near-coordinate fallback to unconditional packet-coordinate lerping in the snapshots labelled `1.16.2` and later: `1.16.1` retains the fallback; `1.16.2`, `.3`, `.4`, and `.5` use the packet coordinates directly. Thus **1.16.2 is the first changed available snapshot**. The exact earliest release in the requested `.0`–`.5` interval is not established: the `.0` source tree is absent, and the `.1` and `.2` trees have no provenance manifests. `.3` and `.4` are manifest-backed; `.5` is unmanifested. Treat “introduced in 1.16.2” as conditional on the unmanifested folder labels.

The relevant method is generic `Entity` handling, not a `LocalPlayer`-specific method. In every present body, `setPacketCoordinates(d, e, f)` runs after entity lookup, before the `!entity.isControlledByLocalInstance()` gate. Only inside that gate does the handler call `lerpTo` and update on-ground state. In 1.15.2 and 1.16.1, when all three absolute differences are strictly below X/Z `0.03125` and Y `0.015625`, it lerps to the entity's current coordinates; otherwise it lerps to packet coordinates. Starting in the `.2` snapshot, that comparison and fallback arm are absent, and the gated lerp always uses packet coordinates. This does **not** mean the whole handler lerps controlled-local instances to packet coordinates.

## Evidence

Canonical read-only root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`.

| Snapshot | Method lines | Method-body SHA-256 | Source-file SHA-256 | Provenance |
|---|---:|---|---|---|
| 1.15.2 | 593–613 | `5be950786a2f32246b17b1506e75955a4c100c7d86250ee90f57c3b8620ea753` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | Manifest absent |
| 1.16.0 | — | — | — | Source directory and manifest absent |
| 1.16.1 | 607–627 | `88df81410d3fcf487ea8e96dd67285028d5ca484d1c24a4163fc19fbdd81b5c2` | `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d` | Manifest absent |
| 1.16.2 | 602–617 | `6c99d1459c414fd400958d0430debe44be5f5d8e40bab2cd046c35cf77205be9` | `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9` | Manifest absent; entire source-file bytes equal manifest-backed 1.16.3 file |
| 1.16.3 | 602–617 | `6c99d1459c414fd400958d0430debe44be5f5d8e40bab2cd046c35cf77205be9` | `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9` | Manifest source hash matches; manifest pins client JAR `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`, Mojmap `client.txt` `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`, and metadata `1.16.3.json` `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead` |
| 1.16.4 | 602–617 | `6c99d1459c414fd400958d0430debe44be5f5d8e40bab2cd046c35cf77205be9` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` | Manifest source hash matches; manifest pins client JAR `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`, Mojmap `client.txt` `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`, and metadata `1.16.4.json` `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5` |
| 1.16.5 | 602–617 | `6c99d1459c414fd400958d0430debe44be5f5d8e40bab2cd046c35cf77205be9` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` | Manifest absent; entire source-file bytes equal manifest-backed 1.16.4 file |

All actual SHA-256 values above were recomputed from the source files. For `.3` and `.4`, each actual source-file hash equals the manifest's `sources[net/minecraft/client/multiplayer/ClientPacketListener.java]` entry. No absent manifest was inferred from directory presence. Method-body hashes were computed over the exact extracted method text (including source line endings).
