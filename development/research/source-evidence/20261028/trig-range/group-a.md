# Group A — relative-input trig alias source comparison

Read-only bounded comparison of `Mth` lookup/trig operations and `Entity#moveRelative` → `getInputVector` in the assigned Mojmap releases. Canonical root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. This is not a full player-tick inventory.

## Exact input identities

For each row, root `mojmap.ready.json` and `mojmap.provenance.json` identify the row's exact version and `mojmap`; the ready marker's declared source-manifest digest matches the on-disk manifest. The listed `Mth.java` and `Entity.java` digests match their entries in `mojmap.sources.sha256` and the source bytes. Provenance hashes below are computed from the on-disk provenance records. Hashes are SHA-256.

| Version | Provenance record | Source manifest | `Mth.java` | `Entity.java` |
|---|---|---|---|---|
| 1.18 | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| 1.18.1 | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| 1.18.2 | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| 1.19 | `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a` | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` | `710e1a1f4e262b610406c707d8fab245d518570dd88b96828ffa9aa5a6fcda65` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` |
| 1.19.1 | `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245` | `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1` | `61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| 1.19.2 | `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | `61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| 1.19.3 | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | `f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` |
| 1.19.4 | `0efcfc6075852711cff5d62c3177258cba273c206c8a2d2642bdf3487c676b34` | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` | `201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` |

## Compared source ranges

Ranges are inclusive; source paths are relative to each version's `mojmap/` directory. Mth ranges cover the lookup declaration/initializer and both trig methods. Entity ranges cover `moveRelative` and the complete `getInputVector` body.

| Version | `net/minecraft/util/Mth.java` | `net/minecraft/world/entity/Entity.java` |
|---|---:|---:|
| 1.18 | 27–49 | 1148–1163 |
| 1.18.1 | 27–49 | 1148–1163 |
| 1.18.2 | 27–49 | 1151–1166 |
| 1.19 | 27–49 | 1175–1190 |
| 1.19.1 | 28–50 | 1170–1185 |
| 1.19.2 | 28–50 | 1170–1185 |
| 1.19.3 | 28–55 | 1175–1190 |
| 1.19.4 | 26–48 | 1181–1196 |

## Equivalence classes and verdict

- **Mth lookup and trig:** all eight releases are semantically equivalent. The table is `float[65536]`; element `i` is `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. `sin(float)` returns `SIN[(int)(angle * 10430.378F) & 65535]`; `cos(float)` uses the same float multiplication plus `16384.0F` before the `int` cast and `65535` mask. Trig signatures, expression types, cast order, and masks are unchanged. (The unrelated `SIN_SCALE` constant is present but these methods inline the literal.)
- **Relative-input rotation:** all eight releases are semantically equivalent. `moveRelative(float, Vec3)` passes `this.getYRot()` to `getInputVector(Vec3, float, float)` and adds its returned vector to delta movement. The accessor returns yaw as `float`; radians are computed as `yaw * (float)(Math.PI / 180.0)`, with float multiplication and a float result passed to the `Mth` float overloads. The zero-length gate, normalization threshold, scale, sin/cos order, and X/Z expressions also match across all assigned sources.
- **Changed operations / first observed endpoints:** none in the assigned versions. No release-to-release semantic change was found; there is no changed-operation endpoint to report. The comparison includes all specified intermediate releases from 1.18 through 1.19.4, with 1.18.2 and 1.19.4 as the requested anchors.



