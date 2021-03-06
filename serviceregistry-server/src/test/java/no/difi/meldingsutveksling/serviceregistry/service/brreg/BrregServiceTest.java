package no.difi.meldingsutveksling.serviceregistry.service.brreg;

import no.difi.meldingsutveksling.serviceregistry.client.brreg.BrregClientImpl;
import no.difi.meldingsutveksling.serviceregistry.model.BrregEnhet;
import no.difi.meldingsutveksling.serviceregistry.model.OrganizationInfo;
import no.difi.meldingsutveksling.serviceregistry.model.OrganizationType;
import no.difi.meldingsutveksling.serviceregistry.model.OrganizationTypes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class BrregServiceTest {
    private static final OrganizationInfo EMPTY_ORGANIZATION_INFO = new OrganizationInfo();
    private BrregService brregService;
    private OrganizationInfo difi;

    @Before
    public void setup() {
        String orgNavn = "DIREKTORATET FOR FORVALTNING OG IKT";
        String orgNr = "991825827";
        final String organisasjonsform = "ORGL";

        difi = new OrganizationInfo.Builder().setOrganizationType(OrganizationTypes.ORGL).withOrganizationName(orgNavn).withOrganizationNumber(orgNr).build();

        BrregClientImpl brregClientMock = setupMock(orgNavn, orgNr, organisasjonsform);
        brregService = new BrregService(brregClientMock);
    }

    @Test
    public void brregHasOrganizationInfo() {
        assertEquals(difi, brregService.getOrganizationInfo(difi.getOrganisationNumber()));
    }

    @Test
    public void brregHasNotOrganizationInfo() {
        assertEquals(EMPTY_ORGANIZATION_INFO, brregService.getOrganizationInfo(""));
    }

    private BrregClientImpl setupMock(String orgNavn, String orgNr, String organisasjonsform) {
        BrregEnhet enhet = new BrregEnhet();
        enhet.setOrganisasjonsnummer(orgNr);
        enhet.setNavn(orgNavn);
        enhet.setOrganisasjonsform(organisasjonsform);

        BrregClientImpl brregClientMock = Mockito.mock(BrregClientImpl.class);
        Mockito.when(brregClientMock.getBrregEnhetByOrgnr(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(brregClientMock.getBrregEnhetByOrgnr(String.valueOf(orgNr))).thenReturn(Optional.of(enhet));
        return brregClientMock;
    }
}
