# Source roster dependency addendum: scan coordinate rounding

`World.containsMaterial` calls the shared `MathHelper.ceil(double)` for the max-Y bound; this source closes the numeric helper dependency.

| Relative source path | 1.12.1 SHA-256 | 1.12.2 SHA-256 |
|---|---|---|
| `net/minecraft/util/math/MathHelper.java` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` |
