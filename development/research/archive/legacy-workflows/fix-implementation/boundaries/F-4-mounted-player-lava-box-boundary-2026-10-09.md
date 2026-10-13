# F-4 mounted-player lava-box stable release boundary

## Finding and review identities

- Accepted source snapshot: commit `019d5fcef181f6dde2a9630e0b882acfa99d6de9`, path `workflows/source-campaign-2026-10-07/1.21.11--26.1.2/findings/F-4-mounted-player-lava-box.md`, blob `bcd14cf6c38270229b035a85f929291254c3664b`, raw-file SHA-256 `a49bfdd5e363f9faae5b99fe29cfefe393a958019460063fbb175ef2b053e034`.
- Independent bounded ACCEPT: commit `964ae707a85126a692de3bf9d41a6db5574867d5`. It accepts the exact fixed mounted-Player witness, not the full pair, broader mounted-fluid layouts, or runtime parity.
- Pair status remains partial/active; full-pair freeze is not claimed.

## Source publication identities

The 26.1 and 26.1.1 source-preparation publication is commit `13dcacb388906800c1e3448633381de69ef51846` on `fix/source-prep-261-2611-2026-10-09`, publication report `workflows/source-campaign-2026-10-07/preparations/26.1-to-26.1.1-unobfuscated-2026-10-09.md`. The consumer roots are the canonical read-only campaign paths under `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/<version>/unobfuscated`.

| Release | Marker SHA-256 | Source-manifest SHA-256 | Artifact-manifest SHA-256 | Diagnostics SHA-256 | Client JAR SHA-256 |
|---|---|---|---|---|---|
| 1.21.11 A | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `a97183dfdbeb5000aac0c66aaed9bb85aa2f2655594e5e4dece254a7c46a13aa` | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` |
| 26.1 | `954126940593c1e1e4b987bfd7079452e0d6283c3d1d97a02693f0319f66f0f5` | `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` | `03e2146ccc20c3f873cea447cd9a5958cf8137524d66641121d6af677a62d2a0` | `6590c89a7d3baf27490ce024d49519ed0dd4ee4e0cc4369c3f419588b15312da` | `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840` |
| 26.1.1 | `29540fe56da79d19a9b125a091cf89cdb45e4493e4c5898c677658dca1474802` | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` | `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81` | `38688a7bf9adf0c34a28264178b4361287d45c6ded444a7ad6f8e41ba9d745fa` | `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` |
| 26.1.2 B | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `b4bd3875263a7155ac32cffd64cfacabc7bd54c82c5fe4082f1ef8a809024c02` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` |

The 26.1 and 26.1.1 ready-marker and source-manifest file bytes were hashed directly in the shared cache and match the publication report. Only the three cited movement source files for each of 26.1, 26.1.1 and 26.1.2 were checked against their source manifests; no whole-tree rehash was performed.

## Paired operation evidence

### 1.21.11: old scan behavior

Source root: `ready/1.21.11/mojmap`. `net/minecraft/world/entity/Entity.java` SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`; manifest entry verified. `updateInWaterStateAndDoFluidPushing()` lines 1501-1506 performs the separate LAVA scan after `updateInWaterStateAndDoWaterCurrentPushing()`. The water method lines 1509-1522 skips only WATER when the vehicle is a non-underwater `AbstractBoat`. `updateFluidHeightAndDoFluidPushing()` lines 3509-3582 constructs `this.getBoundingBox().deflate(0.001)` at the start of the scan, then uses that box's floor/ceil bounds for fluid cells. Thus the mounted Player's LAVA scan uses the deflated Player box; the water-only boat guard does not change that LAVA input. The cited `AbstractBoat.java` hash is `e7d22b0d7a7c204eea153d1dcac7f74b4e4738fe33bdd613aff9ebcc924aa3cf`.

### 26.1: first changed stable release

Source root: `ready/26.1/unobfuscated`. The directly verified manifest entries and file hashes are:

- `Entity.java`, lines 1558-1580 (`updateFluidInteraction`) and 4015-4023 (`getFluidInteractionBox`): `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`.
- `EntityFluidInteraction.java`, lines 32-101 (`update` scan): `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62`.
- `AbstractBoat.java`, lines 783-795 (`modifyPassengerFluidInteractionBox`): `8d681d6ac6e93a648640fd39cde921cc8ed9d4aec03a49e9e43b28cb7be840`.

`getFluidInteractionBox()` first deflates the Entity box, then invokes the vehicle's `modifyPassengerFluidInteractionBox`. `EntityFluidInteraction.update()` obtains that box once and derives its x/y/z scan bounds from it before visiting fluid cells. For a non-underwater boat, the boat modifier raises the box minimum Y to `boatBox.maxY` (or returns null if the boat box reaches above the passenger box). The fluid scan therefore no longer uses the full deflated Player box. This is the accepted 26.1.2 changed operation present in the earliest intervening stable release.

## Cutover check through the endpoint

The same three target files were checked in each exact version and match byte-for-byte across 26.1, 26.1.1 and 26.1.2:

| File | 26.1 | 26.1.1 | 26.1.2 |
|---|---|---|---|
| `Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | same | same |
| `EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` | same | same |
| `AbstractBoat.java` | `8d681d6ac6e93a648640fd39cde921cc8ed9d4aec03a49e9e43b28cb7be840` | same | same |

Each hash was recomputed from the cited file and matched the exact per-version source-manifest entry. The three files establish that the clipped-box behavior is present in 26.1 and remains unchanged in 26.1.1 and 26.1.2. The first changed stable release in this interval is therefore **26.1**. No pre-release behavior or release outside the accepted stable-version interval is claimed.

## Resolver boundary and review status

`ParkourVersion.V26_1` currently covers `26.1`, `26.1.1` and `26.1.2`, so the first-changed stable release maps to the existing `V26_1` boundary; no enum split is indicated by this evidence. The implementation change should emulate `V26_1`, leaving selected `V1_21_11` on the old deflated Player-box behavior and native `CURRENT` unchanged by resolver policy.

This memo is a source-evidence checkpoint for independent boundary review. It does not close pair discovery, expand the accepted mounted witness, or claim runtime parity. Code implementation and independent technical review remain separate statuses.
