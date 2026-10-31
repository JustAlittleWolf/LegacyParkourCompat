$base = 'D:/Javastuff/LegacyParkourCompat/decompiled_minecraft'
$roster = Import-Csv '.native-source/20261031/1165-1171-finish45/block-provider-audit/frozen-roster.tsv' -Delimiter "`t"
$names = 'getShape|getCollisionShape|getFriction|getSpeedFactor|getFluidState|entityInside|stepOn|fallOn|updateEntityAfterFallOn|isClimbable|isLadder'
$rows = [System.Collections.Generic.List[object]]::new()
foreach ($entry in $roster) {
  $relativePath = $entry.relativePath
  if (-not $relativePath.EndsWith('.java')) { continue }
  foreach ($version in @('1.16.5','1.17','1.17.1')) {
    $file = "$base/$version/mojmap/$relativePath"
    if (-not (Test-Path -LiteralPath $file)) { continue }
    $text = Get-Content -LiteralPath $file -Raw
    $pattern = "(?m)^\s*(?:public|protected|private)\s+(?:static\s+)?[^;{}\n]*?\b($names)\s*\("
    $matches = [regex]::Matches($text, $pattern)
    $ordinals = @{}
    foreach ($match in $matches) {
      $method = $match.Groups[1].Value
      $openParen = $text.IndexOf('(', $match.Index + $match.Length - 1)
      $depth = 0; $closeParen = -1
      for ($i=$openParen; $i -lt $text.Length; $i++) {
        if ($text[$i] -eq '(') { $depth++ }
        elseif ($text[$i] -eq ')') { $depth--; if ($depth -eq 0) { $closeParen=$i; break } }
      }
      if ($closeParen -lt 0) { continue }
      $bodyStart = $text.IndexOf('{', $closeParen)
      $semicolon = $text.IndexOf(';', $closeParen)
      if ($bodyStart -lt 0 -or ($semicolon -ge 0 -and $semicolon -lt $bodyStart)) { continue }
      $braceDepth = 0; $bodyEnd = -1
      for ($i=$bodyStart; $i -lt $text.Length; $i++) {
        if ($text[$i] -eq '{') { $braceDepth++ }
        elseif ($text[$i] -eq '}') { $braceDepth--; if ($braceDepth -eq 0) { $bodyEnd=$i; break } }
      }
      if ($bodyEnd -lt 0) { continue }
      if (-not $ordinals.ContainsKey($method)) { $ordinals[$method]=0 }
      $ordinals[$method]++
      $body = (($text.Substring($bodyStart+1, $bodyEnd-$bodyStart-1) -replace '\s+', ' ').Trim())
      $hash = [Convert]::ToHexString([Security.Cryptography.SHA256]::HashData([Text.Encoding]::UTF8.GetBytes($body))).ToLowerInvariant()
      $line = $text.Substring(0,$match.Index).Split("`n").Count
      $rows.Add([pscustomobject]@{relativePath=$relativePath;method=$method;ordinal=$ordinals[$method];version=$version;line=$line;bodySha256=$hash})
    }
  }
}
$rows | Export-Csv '.native-source/20261031/1165-1171-finish45/block-provider-audit/method-fingerprints.tsv' -Delimiter "`t" -NoTypeInformation -Encoding utf8
"fingerprinted=$($rows.Count)"
