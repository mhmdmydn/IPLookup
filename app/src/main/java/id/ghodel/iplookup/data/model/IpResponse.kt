package id.ghodel.iplookup.data.model
import androidx.annotation.Keep

@Keep
data class IpResponse(
    val asn: String,
    val city: String,
    val continent_code: String,
    val country: String,
    val country_area: Int,
    val country_calling_code: String,
    val country_capital: String,
    val country_code: String,
    val country_code_iso3: String,
    val country_name: String,
    val country_population: Int,
    val country_tld: String,
    val currency: String,
    val currency_name: String,
    val in_eu: Boolean,
    val ip: String,
    val languages: String,
    val latitude: Double,
    val longitude: Double,
    val org: String,
    val postal: Any,
    val region: String,
    val region_code: String,
    val timezone: String,
    val utc_offset: String,
    val version: String
)