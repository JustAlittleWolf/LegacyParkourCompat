# Exact packaged fluid-tag audit: 1.15 → 1.15.1

## Input identities

- Authorized raw manifest: `resource-comparison.json`; SHA-256 `bf5d21e3662e00fbd4712637097ce09d96cec4ae2da583f2375dfb969f323a91`.
- Authorized resource hash list: `resources.sha256`; SHA-256 `50ef175e75c3ba140d37453f0d88dd1b4ed5773c8dd971b4d91349e51334c5a7`.
- Exact pair: stable releases 1.15 → 1.15.1; official client metadata is `release` type with matching ID for each version.
- Published Mojmap readiness: both records say `ready`; source and artifact manifests match their SHA-256 pins, source counts, and artifact counts.

| Release | metadata SHA-256 | ready record SHA-256 | client bytes | official client SHA-1 | client SHA-256 | Mojmap source manifest SHA-256 | artifact manifest SHA-256 |
|---|---|---|---:|---|---|---|---|
| 1.15 | `d154eaf07885a7fd6dfc6014bb9caf08fecc19ccd9a0862b31b1c7d1639e7785` | `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` | 15517346 | `7b07fd09d1e3aae1bc7a1304fedc73bfe5d81800` | `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | `acc29974470e4f4864c695e1f1d084e746874663c47a365ca1fa41ff9fcc0cae` |
| 1.15.1 | `dda0859bca7447ff5f071643006b35a51f54e2fbef851a6665b424cf5743c598` | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` | 15518397 | `8b11614bea9293592a947ea8f4fd72981ea66677` | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `d2c40f0770820752fe879670b35bd718b87e4f14491b3ec782e2e74df6b38bbb` |

## Direct archive roster and raw entry verdicts

Both official client ZIPs contain exactly two entries under `data/minecraft/tags/fluids/`: `lava.json` and `water.json`. Raw bytes were read from the ZIP entries and compared byte-for-byte against the four authorized recovered copies.

| Release | ZIP entry | bytes | entry SHA-256 | `replace` | `values` |
|---|---|---:|---|---|---|
| 1.15 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | `false` | `minecraft:lava, minecraft:flowing_lava` |
| 1.15 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | `false` | `minecraft:water, minecraft:flowing_water` |
| 1.15.1 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | `false` | `minecraft:lava, minecraft:flowing_lava` |
| 1.15.1 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | `false` | `minecraft:water, minecraft:flowing_water` |

Water raw JSON (exact visible line breaks and spaces):

```json
{
  "replace": false,
  "values": [
    "minecraft:water",
    "minecraft:flowing_water"
  ]
}
```

Lava raw JSON (exact visible line breaks and spaces):

```json
{
  "replace": false,
  "values": [
    "minecraft:lava",
    "minecraft:flowing_lava"
  ]
}
```

Both objects have exactly the `replace` and `values` properties. Neither values array contains a `#` tag include.

## Reproducible audit identities

Canonical roster bytes: UTF-8 without BOM, LF terminated, sorted by release then path, with actual tab separators for release, archive path, byte length and raw entry SHA-256. Displayed `\t` denotes a tab byte.
`1.15\tdata/minecraft/tags/fluids/lava.json\t92\tf3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd`
`1.15\tdata/minecraft/tags/fluids/water.json\t94\t698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18`
`1.15.1\tdata/minecraft/tags/fluids/lava.json\t92\tf3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd`
`1.15.1\tdata/minecraft/tags/fluids/water.json\t94\t698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18`

Roster SHA-256: `4c8f6c16e6bd27f1d10f1fab337ab0671b42f23fd3b2c2e3b770740c1ce2e54a`.

PASS — For official packaged stable 1.15 and 1.15.1 client archives, the complete fluid-tag resource roster is exactly lava.json and water.json in each release. Each of the four raw entries matches the recovered bytes and author hash list; versions are byte-identical per tag, replace is false, and the only values are the two explicit built-in default fluid IDs for that tag. No tag includes occur in these files. This finding is limited to these packaged defaults and does not establish runtime behavior, datapack overrides, or other releases.

Verdict SHA-256 (UTF-8 without BOM, exact verdict sentence above): `f3f844b3b3df4cb77237288c2cc6126f3ad78e0bbffd5f701172e43ac81062e7`.
