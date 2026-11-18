# Independent binding resolution: 1.9.4 → 1.10 near-vertical Elytra look

## Literal-file identity check

Windows host, PowerShell. SHA-256 and byte lengths were read directly from the two literal files using `Get-FileHash -LiteralPath` and `Get-Item -LiteralPath`:

```text
Path   : C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-194-110-near-vertical-look-alias\report.md
Length : 5756
SHA256 : 3F9EF667C4D326A3ADAFD1E5B7F12EDC920B630695651D8B4C7B15C701BB7651

Path   : C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-194-110-near-vertical-look-alias-audit\verdict.md
Length : 6790
SHA256 : E9CA8B0AB7788B94E508CAE0D81F79D3D54AB91505C841F4DD3FDDB78E6BD687
```

An independent Python `Path.read_bytes()` check was attempted with `py -3`, but no Python installation is available in this environment. The direct PowerShell identity check succeeded.

## Resolution

The audit verdict’s immutable-input line records report SHA-256 `3f9ef667c4d326a3adaf1d5e7bf12edc920b630695651d8b4c7b15c701bb7651`, which does **not** match the report file's actual SHA-256 above. The curator's claimed digest `3f9ef667c4d326a3adafd1e5b7f12edc920b630695651d8b4c7b15c701bb7651` matches the actual report digest (case-insensitive).

I independently compared the report's bounded claim with the original verdict. The report limits its no-difference description to ordinary dry movement for an already-flying local player, at finite negative near-limit pitch with nonzero horizontal look component. The audit expressly accepts that same bounded path, traces its lookup-derived look vector and glide branch math through the first movement call, and excludes exact vertical/aliased states and broader coverage. The report's stated bounded no-difference description therefore matches the audit's accepted scope and conclusion; the discrepancy is in the recorded input digest only.

**Independently rebound acceptance:** ACCEPT the unchanged bounded no-difference claim in the literal report identified by SHA-256 `3F9EF667C4D326A3ADAFD1E5B7F12EDC920B630695651D8B4C7B15C701BB7651` (5,756 bytes), based on the original audit verdict identified by SHA-256 `E9CA8B0AB7788B94E508CAE0D81F79D3D54AB91505C841F4DD3FDDB78E6BD687` (6,790 bytes). This resolution rebinds the existing bounded acceptance to the verified report bytes; it does not expand the accepted scope or claim full-pair coverage.

---

Independent tie-breaker: `/root/c_near_vertical_194_110_binding_resolution`
