# Independent correction audit: 1.11.2 → 1.12 MovingBlock digest

Verdict: **ACCEPT** for the two bounded correction reports identified below. This accepts only their evidence-binding correction; prior technical claims retain their original limited scope and audits.

## Bound corrected reports

- MovingBlock provider payload r2: `build/source-early-patches-20261117/1.11.2--1.12/dependencies/moving-block-provider-payload-r2.md`, SHA-256 `d7e6e3b3bc18aca6f5bb1a83620980a70f620c5fae248deff34e41f6e642a13c`.
- Piston/portal response r3: `build/source-early-patches-20261117/1.11.2--1.12/dependencies/piston-portal-player-response-r3.md`, SHA-256 `ea8fdcfbf1ddf6d1992c6190a46ef26c2882c9c748da1ce77f76f16447355db8`.

The provider correction is bound to original report SHA-256 `46ef94ac4acb8ac3f0f05d675059f5dd4e8f4821c1c62633e6d78bd7ff9da694` and its REQUEST CHANGES verdict SHA-256 `458380c29c8f86ed12f5c1a48a7bc32a7e62650a9ec6ca61c6e0a833ecd96fe5`. The portal correction is bound to report r2 SHA-256 `84f5160938a961bcd3de8bab9f066b9909ec1162bf4055de37ca6f53224d920b` and its prior ACCEPT gate verdict SHA-256 `127b73971637681565c1ce2d9a3be8ce8c6b70fdee7db3053cbc20a0812ad175`.

## Evidence-binding result

The exact `MovingBlockEntity.java` bytes at both endpoints hash to `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e`. The 1.11.2 source-manifest row and 1.12 provenance entry contain that same digest. The 1.11.2 manifest and ready-marker hashes match `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` and `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; the 1.12 provenance file hash matches `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.

For each report, comparison with its named original after excluding the appended correction note confirms the body changes only by inserting the omitted `c` in the repeated MovingBlockEntity digest. The assignment's displayed reference digest `a55ee14227fe6ac0932283ba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` is 63 characters; the exact source and manifest digest above is 64 characters and is the value present in both corrected reports.

This verdict carries forward the provider audit's finding that its only identified blocker was the incorrect digest, and the portal wording audit's existing limited ACCEPT. It does not re-audit provider behavior or portal gates, change either report's technical claims, resolve the open lifecycle boundary, establish complete pair coverage, or freeze the pair.
