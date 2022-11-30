<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" version="5.0" encoding="UTF-8" indent="yes"/>
<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
    <xsl:template match="/">
        <table border ="4">
            <tr bgcolor ="#9acd32">
                <th>ID</th>
                <th>vezeteknek</th>
                <th>keresztnev</th>
                <th>becenev</th>
                <th>kor</th>
            </tr>
            <xsl:for-each select="class/student">
                <tr>
                    <td><xsl:value-of select = "@id"/></td>
                    <td><xsl:value-of select = "vezeteknev"/></td>
                    <td><xsl:value-of select = "keresztnev"/></td>
                    <td><xsl:value-of select = "becenev"/></td>
                    <td><xsl:value-of select = "kor"/></td>
                </tr>
            </xsl:for-each>
        </table>
    </template>
</stylesheet>
    

            
            
            
                
