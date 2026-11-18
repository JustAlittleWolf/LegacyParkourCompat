# Exact packaged water tag evidence

Recovered from official Mojang client artifacts using the pinned local launcher manifest. The version metadata files and client JARs are retained in the sibling `packaged-water-tag-recovery/` scratch directory; this repository contains no binaries.

| Release | Version metadata URL | Metadata SHA-1 | Official client URL | Client SHA-1 | Client SHA-256 | Size |
|---|---|---|---|---|---|---:|
| 1.21.11 | `https://piston-meta.mojang.com/v1/packages/718cd84b4912691fe5de829aab6876a4b7f6c5c4/1.21.11.json` | `718cd84b4912691fe5de829aab6876a4b7f6c5c4` | `https://piston-data.mojang.com/v1/objects/ba2df812c2d12e0219c489c4cd9a5e1f0760f5bd/client.jar` | `ba2df812c2d12e0219c489c4cd9a5e1f0760f5bd` | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | 31,152,600 |
| 26.1.2 | `https://piston-meta.mojang.com/v1/packages/8cca2c0efc47ef22351e5073bb1f5e7ab339279b/26.1.2.json` | `8cca2c0efc47ef22351e5073bb1f5e7ab339279b` | `https://piston-data.mojang.com/v1/objects/4e618f09a0c649dde3fdf829df443ce0b8831e65/client.jar` | `4e618f09a0c649dde3fdf829df443ce0b8831e65` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` | 38,113,927 |

The metadata SHA-1 values match the local launcher manifest pins. The client SHA-1 values match each metadata document's `downloads.client.sha1`; each client SHA-256 matches the exact requested artifact pin.

## ZIP entry

Both verified client JARs contain `data/minecraft/tags/fluid/water.json` (uncompressed length 74 bytes, compressed length 54 bytes). The uncompressed entry payload SHA-256 in each archive is `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004`.

Exact UTF-8 payload, including line endings:

```text
{
  "values": [
    "minecraft:water",
    "minecraft:flowing_water"
  ]
}
```

Ordered member list: `minecraft:water`, `minecraft:flowing_water`. The `replace` field is absent. ZIP entry counts: 28,163 (1.21.11) and 30,675 (26.1.2).