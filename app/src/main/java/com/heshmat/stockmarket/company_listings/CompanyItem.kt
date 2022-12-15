package com.heshmat.stockmarket.company_listings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heshmat.domain.model.CompanyListing
import com.heshmat.stockmarket.ui.theme.StockMarketTheme

@Composable
fun CompanyItem(
    modifier: Modifier = Modifier,
    company: CompanyListing
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = company.name,
                    style = MaterialTheme.typography.titleMedium
                        .copy(
                            color = MaterialTheme.colorScheme.onBackground
                        ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = company.exchange,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "(${company.symbol})",
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onBackground
            )

        }
    }
}

@Composable
@Preview(showBackground = true)
@Preview("dark mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun Preview(){
    StockMarketTheme() {
            CompanyItem(
                Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp),
                CompanyListing("Tesla","A","exchange")
            )
    }

}