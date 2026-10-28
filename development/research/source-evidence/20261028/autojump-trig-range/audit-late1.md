# Lookup matrix audit: rows 1.19–1.20.6

Reviewed only matrix rows 1.19 through 1.20.6, inclusive, from `lookup-acceptance-matrix.md` at SHA-256 `13318f2dd74f66605fa108422031fc0fe43bb944e803fba05f41dfa50b2e696`.

For each row, the canonical consumer was `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<release>\mojmap\net\minecraft\client\player\LocalPlayer.java`. The listed call lines contain direct `Mth.sin` and `Mth.cos` calls using `this.getYRot() * (float) (Math.PI / 180.0)`. I recomputed each consumer SHA-256. Ready/manifests rows have a ready sidecar and an exact consumer-hash entry in `mojmap.sources.sha256`; older-provenance rows have that exact consumer hash in `mojmap/.decompile-provenance.json` under `net/minecraft/client/player/LocalPlayer.java`.

| Release | Direct call lines (`sin`, `cos`) | Recomputed consumer SHA-256 | Identity evidence | Verdict |
|---|---:|---|---|---|
| 1.19 | 995, 996 | `c5f346728f551d278e88b457e54e539885e0d8e549ed7394887d090f28224805` | Ready + source manifest match | Match |
| 1.19.1 | 1001, 1002 | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` | Ready + source manifest match | Match |
| 1.19.2 | 1001, 1002 | `36ae4aabd609b457fffb7a8b14abb50db9ac775857dde1774c0c68a8cf50deef` | Ready + source manifest match | Match |
| 1.19.3 | 922, 923 | `64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479` | Ready + source manifest match | Match |
| 1.19.4 | 902, 903 | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | Ready + source manifest match | Match |
| 1.20 | 894, 895 | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` | Hidden provenance source-hash match; run details missing | Match |
| 1.20.1 | 894, 895 | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` | Ready + source manifest match | Match |
| 1.20.2 | 893, 894 | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | Ready + source manifest match | Match |
| 1.20.3 | 893, 894 | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | Hidden provenance source-hash match; run details missing | Match |
| 1.20.4 | 893, 894 | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | Ready + source manifest match | Match |
| 1.20.5 | 900, 901 | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | Ready + source manifest match | Match |
| 1.20.6 | 900, 901 | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` | Ready + source manifest match | Match |

All 12 assigned rows match the matrix for the direct trig calls, consumer path/hash, and identity class. No discrepancies found in this subset.
