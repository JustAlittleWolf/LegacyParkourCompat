# F-FLYING-CRAWL-SNEAK-RESCALE: flight cancels crawl slowdown only in 1.14.4

1.14.4, mojmap: `1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 618–777.
1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `aiStep`, lines 625–783.

Difference: Reachable precondition: a non-spectator local player is ability-flying, is the controlled camera, is outside water in a dry crawl pose, and holds a directional key plus sneak/shift. The crawl pose can persist in a low passage with enough space for the 0.6-high swimming pose but not the 1.5-high crouching/sneaking pose; while flying, both pose selectors choose standing and then fall back to swimming in that passage. Both keyboard handlers first scale horizontal input by 0.3 because the caller identifies the dry crawl pose as slow movement. 1.14.4 then divides the horizontal impulses by 0.3 when sneak is held during controlled flight; 1.15.2 has no corresponding compensation. The final movement vector is consequently full-scale in 1.14.4 and 0.3-scale in 1.15.2 for a cardinal direction. In clear flight without the crawl pose, 1.14.4's key-triggered 0.3 reduction is canceled by division and 1.15.2 applies neither operation, so the ordinary clear-space case remains equivalent.

Movement meaning: `LocalPlayer#aiStep()` samples input before its controlled-flight adjustment and then calls `super.aiStep()`.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F-FLYING-CRAWL-SNEAK-RESCALE: flight cancels crawl slowdown only in 1.14.4. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.14.4, 1.15.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.14.4",
      "mapping": "mojmap",
      "path": "1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": [
        [
          618,
          777
        ]
      ],
      "status": "verified-content",
      "sha256": "0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "aiStep",
      "lines": [
        [
          625,
          783
        ]
      ],
      "status": "verified-content",
      "sha256": "3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
