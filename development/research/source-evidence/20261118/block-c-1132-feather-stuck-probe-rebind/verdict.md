# Independent source-only rebind audit: 1.13.2 Ornithe Feather

**Terminal result: COMPLETED**

**Prior comparison:** `c-stuck-probe-control/comparison.md`, SHA-256 `c38f62e59babade51760952d6ad83bedf83eb4f4c9fe38d3cee1cab104495285`.

**Prior audit:** `c-stuck-probe-control-audit/verdict.md`, SHA-256 `6e11f6278c0a7fccf2f41d359c254fe0e2cf26fcbf31463eadd718d8dad2f03e`.

**Rebind decision:** Yes. The existing source-only call/control and direct-response verdict can be explicitly rebound to the first-run generated `1.13.2 / ornithe-feather` output, limited to the reviewed claims and the caveats below. This rebind does not extend pair coverage, establish broader movement parity, or assert that the new artifact is byte-equivalent or behaviorally equivalent to the unavailable original client identified as `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5`.

## Independently checked

- The first-run `first-bindings.json` SHA-256 is `85e90b185e328fb0eee1f09db8f63ed01effcfec771be8d3d5e1fd71f16a4c8a`. Its referenced `first-source-provenance.json` SHA-256 matches the recorded `sourceProvenanceSha256` `cc4ef194e3d7ca445cb4403fdf1311cc8ddbd1412b922f78726c8b1bcfb9c291`.
- The generated mapped JAR raw SHA-256 is `847244506ec99f4dac6b816c4acf11e6d0f4f88e0fb3976388661847c679dbc0`, matching the binding. Its ZIP contains 4,265 unique entry paths, including 4,027 `.class` entries, matching the declared counts. The receipt declares canonical content SHA-256 `38a6f3fc763e73bff1cf121f7b09c7508af0fb2e381b70d3754b6aa1c90aca6d` and reviewed-class SHA-256 `bb18f4cc6a86a850eaddcecfaadcef97c4ce9a20006fc66a1f979f78d2acdab3`; those content digests were not independently recomputed in this bounded audit.
- The receipt pins the 1.13.2 Mojang version-metadata URL/hash (`26ddcd…e8c95`), client object URL/hash (`341088…5c0f9`), Feather 1.13.2 metadata URL/hash (`6110b2…8f3a`), and merged-v2 Feather artifact URL/hash (`317384…7283b`). The same input map records those alongside the source pipeline/tool identities. These are recorded pins, not independently re-hashed raw-input artifacts: the permitted first-run receipts contain no raw client, metadata bodies, Feather mapping archive or separate Tiny file. Thus the artifact-to-input chain is receipt-asserted, not revalidated from those raw bytes here.
- The frozen probe manifest SHA-256 is `016096358f0ceb5a527c452ab877e54fc9be16c7da3a56e435d991974c50f049`, as assigned. All 11 frozen 1.13.2 source files match both their manifest hashes and corresponding entries in the bound generated source manifest. In particular, `LocalClientPlayerEntity.java` is `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`, the exact hash in the prior comparison. I rechecked the frozen `mobTick` and `pushAwayFrom` bodies at the cited ranges: four probes retain W/S, W/N, E/N, E/S order before food/sprint logic; `noClip` is checked inside each helper; candidate selection is W/E/N/S with strict closer-than comparisons; and the selected response writes only the corresponding horizontal component using `±0.1F`.
- The first-run binding records 2,711 generated sources, 2,711 canonical sources, and equality for all 2,711. The generated source manifest has 2,711 entries. The canonical source tree and original source-manifest bytes are outside the permitted inputs, so equality across the full tree and the recorded `originalSourceManifestSha256` `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` remain recorded assertions rather than independent checks in this audit. The frozen relevant-file comparison does independently support the narrow source claim needed for this rebind.

## Scope retained from the prior verdict

The rebind carries forward only the already reviewed 1.13.2 caller/control and direct-response statements against the exact source hash above. It preserves the prior 1.13.2 versus later-version distinctions and precision caveat; it makes no new geometry/occupancy finding, tick-coverage claim, implementation claim or runtime-parity claim. The original missing-artifact limitation remains open. Forced reproduction is separate evidence and was not inspected for this first-run rebind.
